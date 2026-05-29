package com.agentinter.pcphone.core.network

import android.util.Log
import com.agentinter.pcphone.core.model.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.serialization.json.*
import okhttp3.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import java.util.UUID
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

enum class ConnectionState {
    Disconnected,
    Connecting,
    Connected,
    Reconnecting;

    val isConnected: Boolean get() = this == Connected
}

class GatewayClient(
    private val host: String = "127.0.0.1",
    private val port: Int = 18789,
    private val token: String = ""
) {
    companion object {
        private const val TAG = "GatewayClient"
    }

    private val json = Json { ignoreUnknownKeys = true; isLenient = true }

    private val _connectionState = MutableStateFlow(ConnectionState.Disconnected)
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    private val _events = MutableSharedFlow<GatewayEvent>(extraBufferCapacity = 64)
    val events: SharedFlow<GatewayEvent> = _events.asSharedFlow()

    private val _pendingRequests = ConcurrentHashMap<String, CompletableDeferred<JsonObject>>()

    @Volatile
    private var okHttpClient: OkHttpClient? = null
    @Volatile
    private var webSocket: WebSocket? = null

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var connectJob: Job? = null
    private var reconnectJob: Job? = null

    // 鈹€鈹€ WebSocket listener 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

    private val webSocketListener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            Log.d(TAG, "WebSocket opened, waiting for challenge...")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            try {
                val root = json.parseToJsonElement(text).jsonObject
                handleMessage(root)
            } catch (e: Exception) {
                Log.w(TAG, "Failed to parse message: ${e.message}")
            }
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            Log.d(TAG, "WebSocket closing: $code $reason")
            webSocket.close(1000, null)
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            Log.d(TAG, "WebSocket closed: $code $reason")
            cancelAllPending(PacketClosedException("WebSocket closed: $code $reason"))
            if (_connectionState.value == ConnectionState.Connected ||
                _connectionState.value == ConnectionState.Connecting
            ) {
                scope.launch { reconnect() }
            }
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Log.e(TAG, "WebSocket failure: ${t.message}", t)
            cancelAllPending(PacketClosedException("WebSocket failure: ${t.message}"))
            if (_connectionState.value == ConnectionState.Connected ||
                _connectionState.value == ConnectionState.Connecting
            ) {
                scope.launch { reconnect() }
            }
        }
    }

    // 鈹€鈹€ Message dispatcher 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

    private fun handleMessage(root: JsonObject) {
        // RPC response: has "id" field
        val id = root["id"]?.jsonPrimitive?.contentOrNull
        if (id != null) {
            val deferred = _pendingRequests.remove(id)
            if (deferred != null) {
                val result = root["result"]?.jsonObject
                if (root["ok"]?.jsonPrimitive?.booleanOrNull != false && result != null) {
                    deferred.complete(result)
                } else {
                    val errorMsg = root["error"]?.jsonObject?.let {
                        "code=${it["code"]}, message=${it["message"]?.jsonPrimitive?.contentOrNull}"
                    } ?: "unknown error"
                    deferred.completeExceptionally(PacketException(errorMsg))
                }
            }
            return
        }

        // Gateway event: has "event" field
        val eventType = root["event"]?.jsonPrimitive?.contentOrNull
        if (eventType != null) {
            if (eventType == "connect.challenge") {
                handleChallenge(root)
            } else {
                val gatewayEvent = try {
                    json.decodeFromJsonElement<GatewayEvent>(root)
                } catch (e: Exception) {
                    Log.w(TAG, "Failed to decode event: ${e.message}")
                    null
                }
                if (gatewayEvent != null) {
                    _events.tryEmit(gatewayEvent)
                }
            }
            return
        }

        // Fallback: bare "type":"event"
        val type = root["type"]?.jsonPrimitive?.contentOrNull
        if (type == "event") {
            val gatewayEvent = try {
                json.decodeFromJsonElement<GatewayEvent>(root)
            } catch (e: Exception) {
                Log.w(TAG, "Failed to decode generic event: ${e.message}")
                null
            }
            if (gatewayEvent != null) {
                _events.tryEmit(gatewayEvent)
            }
        }
    }

    // 鈹€鈹€ Challenge-response auth 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

    private fun handleChallenge(root: JsonObject) {
        try {
            val payload = root["payload"]?.jsonObject
                ?: throw PacketException("Missing payload in challenge")
            val nonce = payload["nonce"]?.jsonPrimitive?.content
                ?: throw PacketException("Missing nonce in challenge")
            val signature = computeSignature(token, nonce)

            val responseJson = buildJsonObject {
                put("type", "connect.response")
                put("payload", buildJsonObject {
                    put("signature", signature)
                })
            }

            webSocket?.send(json.encodeToString(JsonObject.serializer(), responseJson))
            Log.d(TAG, "Sent connect.response (HMAC-SHA256)")
            _connectionState.value = ConnectionState.Connected
        } catch (e: Exception) {
            Log.e(TAG, "Challenge handling failed: ${e.message}", e)
            webSocket?.close(1000, "Auth failed")
            _connectionState.value = ConnectionState.Disconnected
        }
    }

    // 鈹€鈹€ HMAC-SHA256 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

    private fun computeSignature(token: String, nonce: String): String {
        val mac = Mac.getInstance("HmacSHA256")
        val keySpec = SecretKeySpec(token.toByteArray(Charsets.UTF_8), "HmacSHA256")
        mac.init(keySpec)
        val hash = mac.doFinal(nonce.toByteArray(Charsets.UTF_8))
        return hash.joinToString("") { "%02x".format(it) }
    }

    // 鈹€鈹€ Connect / Disconnect 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

    suspend fun connect() {
        connectJob?.cancelAndJoin()
        connectJob = scope.launch {
            _connectionState.value = ConnectionState.Connecting

            val client = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build()
            okHttpClient = client

            val url = "ws://${host}:${port}"
            val request = Request.Builder().url(url).build()

            Log.d(TAG, "Connecting to $url ...")
            webSocket = client.newWebSocket(request, webSocketListener)
        }
    }

    fun disconnect() {
        reconnectJob?.cancel()
        connectJob?.cancel()
        _connectionState.value = ConnectionState.Disconnected  // 蹇呴』鍦?close() 涔嬪墠锛岄槻姝?onClosed 璇Е鍙?reconnect
        webSocket?.close(1000, "Client disconnect")
        webSocket = null
        okHttpClient?.dispatcher?.executorService?.shutdown()
        okHttpClient?.connectionPool?.evictAll()
        okHttpClient = null
        cancelAllPending(PacketClosedException("Client disconnected"))
        scope.cancel()  // 閲婃斁 CoroutineScope锛岄槻姝㈡硠婕?    }

    // 鈹€鈹€ Reconnect (exponential backoff) 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

    private suspend fun reconnect() {
        reconnectJob?.cancel()
        reconnectJob = scope.launch {
            _connectionState.value = ConnectionState.Reconnecting
            var backoffMs = 1000L
            repeat(10) { attempt ->
                try {
                    connect()
                    // Wait for Connected state (max 5s per attempt)
                    withTimeout(5_000L) {
                        _connectionState.first { it == ConnectionState.Connected }
                    }
                    Log.d(TAG, "Reconnected successfully on attempt ${attempt + 1}")
                    return@launch
                } catch (e: Exception) {
                    Log.w(TAG, "Reconnect attempt ${attempt + 1} failed: ${e.message}")
                }
                delay(backoffMs)
                backoffMs = (backoffMs * 2).coerceAtMost(30_000L)
            }
            Log.e(TAG, "All reconnect attempts exhausted")
            _connectionState.value = ConnectionState.Disconnected
        }
    }

    // 鈹€鈹€ Send RPC request 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

    suspend fun sendRequest(method: String, params: JsonObject? = null): Result<JsonObject> {
        val requestId = UUID.randomUUID().toString()
        val deferred = CompletableDeferred<JsonObject>()
        _pendingRequests[requestId] = deferred

        return try {
            if (_connectionState.value != ConnectionState.Connected) {
                throw PacketNotConnectedException()
            }

            val rpcJson = buildJsonObject {
                put("id", requestId)
                put("method", method)
                if (params != null) {
                    put("params", params)
                }
            }

            val message = json.encodeToString(JsonObject.serializer(), rpcJson)
            webSocket?.send(message) ?: throw PacketNotConnectedException()

            val result = withTimeout(30_000L) { deferred.await() }
            Result.success(result)
        } catch (e: TimeoutCancellationException) {
            Result.failure(PacketException("Request timed out after 30s"))
        } catch (e: CancellationException) {
            Result.failure(PacketException("Request cancelled"))
        } catch (e: PacketNotConnectedException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(PacketException("sendRequest failed: ${e.message}", e))
        } finally {
            _pendingRequests.remove(requestId)
        }
    }

    // 鈹€鈹€ Convenience methods 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

    suspend fun createSession(key: String): Result<SessionsCreateResult> {
        val params = buildJsonObject { put("key", key) }
        val response = sendRequest("sessions.create", params)
        return response.map { json.decodeFromJsonElement<SessionsCreateResult>(it) }
    }

    suspend fun sendMessage(
        sessionKey: String,
        message: String,
        idempotencyKey: String = UUID.randomUUID().toString()
    ): Result<ChatSendResult> {
        val params = buildJsonObject {
            put("sessionKey", sessionKey)
            put("message", message)
            put("idempotencyKey", idempotencyKey)
        }
        val response = sendRequest("chat.send", params)
        return response.map { json.decodeFromJsonElement<ChatSendResult>(it) }
    }

    suspend fun getHistory(sessionKey: String, limit: Int? = null, before: String? = null): Result<JsonObject> {
        val params = buildJsonObject {
            put("sessionKey", sessionKey)
            if (limit != null) put("limit", limit)
            if (before != null) put("before", before)
        }
        return sendRequest("chat.history", params)
    }

    suspend fun abortChat(sessionKey: String, runId: String): Result<JsonObject> {
        val params = buildJsonObject {
            put("sessionKey", sessionKey)
            put("runId", runId)
        }
        return sendRequest("chat.abort", params)
    }

    // 鈹€鈹€ Internal helpers 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

    private fun cancelAllPending(exception: Throwable) {
        _pendingRequests.forEach { (_, deferred) ->
            deferred.completeExceptionally(exception)
        }
        _pendingRequests.clear()
    }
}

// 鈹€鈹€ Exception types 鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€鈹€

open class PacketException(message: String, cause: Throwable? = null) : Exception(message, cause)
class PacketNotConnectedException :
    PacketException("WebSocket is not connected")
class PacketClosedException(message: String) : PacketException(message)
