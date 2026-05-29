package com.agentinter.pcphone.core.network

import android.util.Log
import com.agentinter.pcphone.core.model.SessionsCreateResult
import com.agentinter.pcphone.core.model.SessionInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull
import kotlinx.serialization.json.put

class SessionManager(
    private val client: GatewayClient
) {
    companion object {
        private const val TAG = "SessionManager"
        private const val DEFAULT_SESSION_KEY = "main"
    }

    private val json = Json { ignoreUnknownKeys = true }

    private val _currentSession = MutableStateFlow<SessionInfo?>(null)
    val currentSession: StateFlow<SessionInfo?> = _currentSession

    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady

    /**
     * 鍒濆鍖栦細璇濓細纭繚 "main" 浼氳瘽瀛樺湪
     * 1. 璋?sessions.list 鏌ユ槸鍚︽湁 "main"
     * 2. 鏃犲垯 sessions.create("main")
     * 3. 杩斿洖 sessionKey + sessionId
     */
    suspend fun resolveSession(): Result<SessionInfo> {
        Log.d(TAG, "Resolving session...")
        
        // Step 1: List existing sessions
        val listResult = client.sendRequest(
            method = "sessions.list",
            params = buildJsonObject { }
        )
        
        if (listResult.isSuccess) {
            val result = listResult.getOrThrow()
            Log.d(TAG, "sessions.list response: $result")
            
            // Parse sessions list
            val sessionsArray = result["sessions"]?.jsonArray
            if (sessionsArray != null) {
                for (sessionElement in sessionsArray) {
                    val sessionObj = sessionElement.jsonObject
                    val key = sessionObj["key"]?.jsonPrimitive?.content
                    if (key == DEFAULT_SESSION_KEY || key?.endsWith(":$DEFAULT_SESSION_KEY") == true) {
                        val sessionId = sessionObj["sessionId"]?.jsonPrimitive?.content ?: continue
                        val info = SessionInfo(
                            key = key,
                            sessionId = sessionId,
                            updatedAt = sessionObj["updatedAt"]?.jsonPrimitive?.longOrNull
                        )
                        _currentSession.value = info
                        _isReady.value = true
                        Log.i(TAG, "Found existing session: $key -> $sessionId")
                        return Result.success(info)
                    }
                }
            }
        } else {
            Log.w(TAG, "sessions.list failed: ${listResult.exceptionOrNull()?.message}, will try create")
        }

        // Step 2: Create new session
        Log.d(TAG, "Creating new session '$DEFAULT_SESSION_KEY'...")
        val createResult = client.createSession(DEFAULT_SESSION_KEY)
        
        return if (createResult.isSuccess) {
            val created = createResult.getOrThrow()
            val info = SessionInfo(
                key = created.key,
                sessionId = created.sessionId,
                updatedAt = created.entry?.updatedAt ?: System.currentTimeMillis()
            )
            _currentSession.value = info
            _isReady.value = true
            Log.i(TAG, "Created session: ${created.key} -> ${created.sessionId}")
            Result.success(info)
        } else {
            val error = createResult.exceptionOrNull()
            Log.e(TAG, "Failed to create session: ${error?.message}")
            _isReady.value = false
            Result.failure(error ?: Exception("Unknown session creation error"))
        }
    }

    /**
     * 鑾峰彇褰撳墠浼氳瘽 Key锛堝 "main"锛?     */
    fun getSessionKey(): String {
        return _currentSession.value?.key ?: DEFAULT_SESSION_KEY
    }

    /**
     * 鑾峰彇褰撳墠浼氳瘽 ID
     */
    fun getSessionId(): String? {
        return _currentSession.value?.sessionId
    }

    /**
     * 閲嶇疆浼氳瘽鐘舵€?     */
    fun reset() {
        _currentSession.value = null
        _isReady.value = false
        Log.d(TAG, "Session state reset")
    }
}
