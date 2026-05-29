package com.agentinter.pcphone.core.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

// === 杩炴帴灞?===

@Serializable
data class ConnectChallenge(
    val nonce: String,
    val ts: Long
)

@Serializable
data class ConnectResponse(
    val signature: String
)

// === RPC 甯?===

@Serializable
data class RpcRequest(
    val id: String,
    val method: String,
    val params: JsonObject? = null
)

@Serializable
data class RpcResponse(
    val id: String,
    val ok: Boolean = true,
    val result: JsonObject? = null,
    val error: RpcError? = null
)

@Serializable
data class RpcError(
    val code: Int,
    val message: String
)

// === 浜嬩欢甯?===

@Serializable
data class GatewayEvent(
    val type: String? = null,     // "event"
    val event: String? = null,    // "connect.challenge", "chat", etc.
    val payload: JsonObject? = null
)

// === sessions.create 鍙傛暟鍜屽搷搴?===

@Serializable
data class SessionsCreateParams(
    val key: String
)

@Serializable
data class SessionsCreateResult(
    val ok: Boolean,
    val key: String,
    val sessionId: String,
    val entry: SessionEntry? = null,
    val runStarted: Boolean = false
)

@Serializable
data class SessionEntry(
    val sessionId: String,
    val sessionFile: String,
    val updatedAt: Long
)

// === sessions.list 鍝嶅簲 ===

@Serializable
data class SessionsListResult(
    val sessions: List<SessionInfo> = emptyList()
)

@Serializable
data class SessionInfo(
    val key: String,
    val sessionId: String,
    val updatedAt: Long? = null
)

// === chat.send 鍙傛暟鍜屽搷搴?===

@Serializable
data class ChatSendParams(
    val sessionKey: String,
    val message: String,
    val idempotencyKey: String
)

@Serializable
data class ChatSendResult(
    val runId: String,
    val status: String
)

// === chat.history 鍙傛暟 ===

@Serializable
data class ChatHistoryParams(
    val sessionKey: String,
    val limit: Int? = null,
    val before: String? = null
)

// === chat 浜嬩欢 (娴佸紡娑堟伅) ===

@Serializable
data class ChatEvent(
    val runId: String? = null,
    val message: ChatEventMessage? = null,
    val delta: String? = null,       // 澧為噺鏂囨湰
    val isReasoning: Boolean? = null, // 鎬濊€冩爣璁?    val status: String? = null       // "streaming", "completed", "aborted"
)

@Serializable
data class ChatEventMessage(
    val role: String? = null,
    val content: List<ContentBlock>? = null,
    val timestamp: Long? = null
)

@Serializable
data class ContentBlock(
    val type: String,      // "text"
    val text: String
)

// === chat.abort ===

@Serializable
data class ChatAbortParams(
    val sessionKey: String,
    val runId: String
)
