package com.agentinter.pcphone.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agentinter.pcphone.core.model.Attachment
import com.agentinter.pcphone.core.model.AttachmentType
import com.agentinter.pcphone.core.model.ChatEvent
import com.agentinter.pcphone.core.model.GatewayEvent
import com.agentinter.pcphone.core.model.Message
import com.agentinter.pcphone.core.model.MessageRole
import com.agentinter.pcphone.core.network.ConnectionState
import com.agentinter.pcphone.core.network.GatewayClient
import com.agentinter.pcphone.core.network.SessionManager
import com.agentinter.pcphone.core.storage.AppDatabase
import com.agentinter.pcphone.core.storage.AttachmentEntity
import com.agentinter.pcphone.core.storage.MessageEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long

data class ChatUiState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false,
    val isSending: Boolean = false,
    val isStreaming: Boolean = false,    // 姝ｅ湪鎺ユ敹 AI 娴佸紡鍥炲
    val streamingRunId: String? = null,  // 褰撳墠娴佸紡娑堟伅鐨?runId
    val error: String? = null,
    val connectionStatus: ConnectionState = ConnectionState.Disconnected,
    val sessionReady: Boolean = false,
    val currentRunId: String? = null,
    val hasMore: Boolean = false,
    val isLoadingMore: Boolean = false
)

class ChatViewModel(
    private val client: GatewayClient,
    private val sessionManager: SessionManager,
    private val database: AppDatabase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText.asStateFlow()

    private val json = Json { ignoreUnknownKeys = true; isLenient = true }

    init {
        // 鐩戝惉杩炴帴鐘舵€?        viewModelScope.launch {
            client.connectionState.collect { state ->
                _uiState.update { it.copy(connectionStatus = state) }
            }
        }

        // 鐩戝惉 Gateway 鎺ㄩ€佷簨浠?        viewModelScope.launch {
            client.events.collect { event ->
                handleGatewayEvent(event)
            }
        }

        // 鑷姩杩炴帴
        viewModelScope.launch {
            initialize()
        }
    }

    private suspend fun initialize() {
        _uiState.update { it.copy(isLoading = true) }

        // Step 0: 浠?Room 鍔犺浇鏈湴缂撳瓨锛堢珛鍗冲彲鏄剧ず锛?        loadFromRoom()

        // Step 1: 杩炴帴 Gateway
        client.connect()

        // Step 2: 绛夊緟 challenge-response 瀹屾垚锛堟渶澶?10s锛?        try {
            withTimeout(10_000L) {
                client.connectionState.first { it == ConnectionState.Connected }
            }
        } catch (e: Exception) {
            _uiState.update { it.copy(isLoading = false, error = "鏃犳硶杩炴帴鍒?Gateway") }
            return
        }

        // Step 3: 瑙ｆ瀽浼氳瘽
        val sessionResult = sessionManager.resolveSession()
        if (sessionResult.isFailure) {
            _uiState.update { it.copy(isLoading = false, error = "浼氳瘽鍒濆鍖栧け璐?) }
            return
        }

        _uiState.update { it.copy(sessionReady = true) }

        // Step 4: 鍔犺浇鍘嗗彶
        loadHistory()
    }

    fun updateInputText(text: String) {
        _inputText.value = text
    }

    fun sendMessage() {
        val text = _inputText.value.trim()
        if (text.isEmpty() || _uiState.value.isSending) return

        val idempotencyKey = java.util.UUID.randomUUID().toString()
        val userMessage = Message(
            id = idempotencyKey,
            role = MessageRole.User,
            content = text,
            timestamp = System.currentTimeMillis()
        )

        // 绔嬪嵆鏄剧ず鐢ㄦ埛娑堟伅 + 鍐欏叆 Room
        _uiState.update {
            it.copy(
                messages = it.messages + userMessage,
                isSending = true,
                error = null
            )
        }
        _inputText.value = ""
        viewModelScope.launch { saveToRoom(userMessage) }

        viewModelScope.launch {
            val result = client.sendMessage(
                sessionKey = sessionManager.getSessionKey(),
                message = text,
                idempotencyKey = idempotencyKey
            )

            result.onSuccess { chatResult ->
                _uiState.update {
                    it.copy(
                        currentRunId = chatResult.runId,
                        isSending = false
                    )
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(
                        isSending = false,
                        error = "鍙戦€佸け璐? ${error.message}"
                    )
                }
            }
        }
    }

    fun sendImage(imagePath: String) {
        if (_uiState.value.isSending) return

        val idempotencyKey = java.util.UUID.randomUUID().toString()
        val attachmentId = java.util.UUID.randomUUID().toString()

        val attachment = Attachment(
            id = attachmentId,
            type = AttachmentType.Image,
            name = imagePath.substringAfterLast("/"),
            localPath = imagePath
        )

        val userMessage = Message(
            id = idempotencyKey,
            role = MessageRole.User,
            content = "",
            timestamp = System.currentTimeMillis(),
            attachments = listOf(attachment)
        )

        _uiState.update {
            it.copy(
                messages = it.messages + userMessage,
                isSending = true,
                error = null
            )
        }

        viewModelScope.launch {
            val result = client.sendMessage(
                sessionKey = sessionManager.getSessionKey(),
                message = "[Image]",
                idempotencyKey = idempotencyKey
            )

            result.onSuccess { chatResult ->
                _uiState.update {
                    it.copy(currentRunId = chatResult.runId, isSending = false)
                }
            }.onFailure { error ->
                _uiState.update {
                    it.copy(isSending = false, error = "鍥剧墖鍙戦€佸け璐? ${error.message}")
                }
            }
        }
    }

    fun abortCurrent() {
        val runId = _uiState.value.currentRunId ?: _uiState.value.streamingRunId ?: return
        viewModelScope.launch {
            client.abortChat(sessionManager.getSessionKey(), runId)
        }
    }

    fun sendFile(filePath: String, fileName: String) {
        if (_uiState.value.isSending) return

        val idempotencyKey = java.util.UUID.randomUUID().toString()
        val attachment = Attachment(
            id = java.util.UUID.randomUUID().toString(),
            type = AttachmentType.File,
            name = fileName,
            localPath = filePath
        )

        val userMessage = Message(
            id = idempotencyKey,
            role = MessageRole.User,
            content = "[鏂囦欢: $fileName]",
            timestamp = System.currentTimeMillis(),
            attachments = listOf(attachment)
        )

        _uiState.update {
            it.copy(messages = it.messages + userMessage, isSending = true, error = null)
        }

        viewModelScope.launch {
            val result = client.sendMessage(
                sessionKey = sessionManager.getSessionKey(),
                message = "[File: $fileName]",
                idempotencyKey = idempotencyKey
            )
            result.onSuccess { chatResult ->
                _uiState.update { it.copy(currentRunId = chatResult.runId, isSending = false) }
            }.onFailure { error ->
                _uiState.update { it.copy(isSending = false, error = "鏂囦欢鍙戦€佸け璐? ${error.message}") }
            }
        }
    }

    private suspend fun loadHistory() {
        val result = client.getHistory(sessionManager.getSessionKey(), limit = 50)
        result.onSuccess { jsonObj ->
            // 瑙ｆ瀽鍘嗗彶娑堟伅
            val messagesArray = jsonObj["messages"]?.jsonArray
            if (messagesArray != null) {
                val historyMessages = messagesArray.mapNotNull { element ->
                    parseHistoryMessage(element.jsonObject)
                }
                _uiState.update {
                    it.copy(
                        messages = historyMessages,
                        isLoading = false,
                        hasMore = historyMessages.size >= 50  // 濡傛灉鎷夋弧50鏉★紝鍙兘杩樻湁鏇村
                    )
                }
            } else {
                _uiState.update { it.copy(isLoading = false, hasMore = false) }
            }
        }.onFailure {
            _uiState.update { it.copy(isLoading = false, error = "鍔犺浇鍘嗗彶澶辫触") }
        }
    }

    fun loadMore() {
        if (_uiState.value.isLoadingMore || !_uiState.value.hasMore) return

        val oldestMessage = _uiState.value.messages.firstOrNull() ?: return

        _uiState.update { it.copy(isLoadingMore = true) }

        viewModelScope.launch {
            val result = client.getHistory(
                sessionKey = sessionManager.getSessionKey(),
                limit = 50
            )

            result.onSuccess { jsonObj ->
                val messagesArray = jsonObj["messages"]?.jsonArray
                if (messagesArray != null) {
                    val older = messagesArray.mapNotNull {
                        parseHistoryMessage(it.jsonObject)
                    }
                    // 鍘婚噸鍚堝苟锛氱敤 id 鍘婚噸
                    val existingIds = _uiState.value.messages.map { it.id }.toSet()
                    val newMessages = older.filter { it.id !in existingIds }

                    _uiState.update {
                        it.copy(
                            messages = newMessages + it.messages,  // 鏇存棭鐨勫湪鍓?                            isLoadingMore = false,
                            hasMore = older.size >= 50  // 濡傛灉鎷夋弧50鏉★紝鍙兘杩樻湁
                        )
                    }
                } else {
                    _uiState.update { it.copy(isLoadingMore = false, hasMore = false) }
                }
            }.onFailure {
                _uiState.update { it.copy(isLoadingMore = false) }
            }
        }
    }

    private fun handleGatewayEvent(event: GatewayEvent) {
        if (event.event != "chat") return
        val payload = event.payload ?: return

        try {
            val chatEvent = json.decodeFromJsonElement(ChatEvent.serializer(), payload)
            val runId = chatEvent.runId ?: return

            when (chatEvent.status) {
                "streaming" -> {
                    // 娴佸紡澧為噺锛氳拷鍔?delta 鍒版秷鎭?                    val delta = chatEvent.delta ?: return
                    val isReasoning = chatEvent.isReasoning ?: false

                    _uiState.update { state ->
                        val messages = state.messages.toMutableList()
                        val existingIdx = messages.indexOfLast { it.id == runId }

                        if (existingIdx >= 0) {
                            // 杩藉姞 delta
                            val existing = messages[existingIdx]
                            messages[existingIdx] = existing.copy(
                                content = existing.content + delta,
                                isReasoning = isReasoning
                            )
                            state.copy(messages = messages, isStreaming = true, streamingRunId = runId)
                        } else {
                            // 绗竴鏉?delta锛氬垱寤烘柊娑堟伅
                            val newMsg = Message(
                                id = runId,
                                role = MessageRole.Assistant,
                                content = delta,
                                timestamp = System.currentTimeMillis(),
                                isReasoning = isReasoning
                            )
                            state.copy(
                                messages = messages + newMsg,
                                isStreaming = true,
                                streamingRunId = runId
                            )
                        }
                    }
                }

                "completed" -> {
                    // 娴佸紡瀹屾垚锛氬叧闂?streaming 鐘舵€侊紝鍐欏叆 Room
                    _uiState.update { it.copy(isStreaming = false, streamingRunId = null) }
                    val msg = chatEvent.message
                    if (msg != null && msg.role == "assistant") {
                        val aiMessage = Message(
                            id = runId,
                            role = MessageRole.Assistant,
                            content = msg.content?.joinToString("") { it.text } ?: "",
                            timestamp = msg.timestamp ?: System.currentTimeMillis(),
                            isReasoning = chatEvent.isReasoning ?: false
                        )
                        viewModelScope.launch { saveToRoom(aiMessage) }
                    }
                }

                "aborted" -> {
                    // 琚墦鏂細鏍囪娑堟伅
                    _uiState.update { state ->
                        val messages = state.messages.toMutableList()
                        val existingIdx = messages.indexOfLast { it.id == runId }
                        if (existingIdx >= 0) {
                            val existing = messages[existingIdx]
                            messages[existingIdx] = existing.copy(
                                content = existing.content + "\n\n*(宸蹭腑鏂?*"
                            )
                        }
                        state.copy(messages = messages, isStreaming = false, streamingRunId = null)
                    }
                }

                // 鍏煎鏃ф牸寮忥細鏃?status 浣嗘湁 message
                null -> {
                    val msg = chatEvent.message ?: return
                    if (msg.role != "assistant") return
                    val content = msg.content?.joinToString("") { it.text } ?: return
                    val aiMessage = Message(
                        id = runId,
                        role = MessageRole.Assistant,
                        content = content,
                        timestamp = msg.timestamp ?: System.currentTimeMillis(),
                        isReasoning = chatEvent.isReasoning ?: false
                    )
                    _uiState.update { state ->
                        val existingIdx = state.messages.indexOfFirst { it.id == runId }
                        if (existingIdx >= 0) {
                            state.copy(messages = state.messages.toMutableList().apply { set(existingIdx, aiMessage) })
                        } else {
                            state.copy(messages = state.messages + aiMessage)
                        }
                    }
                    viewModelScope.launch { saveToRoom(aiMessage) }
                }
            }
        } catch (e: Exception) {
            // 瑙ｆ瀽澶辫触闈欓粯璺宠繃
        }
    }

    private suspend fun loadFromRoom() {
        val sessionKey = sessionManager.getSessionKey()
        val cached = database.messageDao().getMessagesWithAttachments(sessionKey, limit = 100)
        if (cached.isNotEmpty()) {
            val messages = cached.map { mwa ->
                Message(
                    id = mwa.message.id,
                    role = when (mwa.message.role) {
                        "user" -> MessageRole.User
                        "assistant" -> MessageRole.Assistant
                        else -> MessageRole.System
                    },
                    content = mwa.message.content,
                    timestamp = mwa.message.timestamp,
                    isReasoning = mwa.message.isReasoning,
                    attachments = mwa.attachments.map { att ->
                        Attachment(
                            id = att.id,
                            type = when (att.type) {
                                "Image" -> AttachmentType.Image
                                "File" -> AttachmentType.File
                                else -> AttachmentType.Image
                            },
                            name = att.name,
                            localPath = att.localPath,
                            url = att.remoteUrl
                        )
                    }
                )
            }
            _uiState.update { it.copy(messages = messages) }
        }
    }

    private suspend fun saveToRoom(message: Message) {
        val entity = MessageEntity(
            id = message.id,
            sessionKey = sessionManager.getSessionKey(),
            role = message.role.name.lowercase(),
            content = message.content,
            timestamp = message.timestamp,
            isReasoning = message.isReasoning
        )
        database.messageDao().insertMessage(entity)

        if (message.attachments.isNotEmpty()) {
            val attachments = message.attachments.map { att ->
                AttachmentEntity(
                    id = att.id,
                    messageId = message.id,
                    type = att.type.name,
                    name = att.name,
                    localPath = att.localPath,
                    remoteUrl = att.url
                )
            }
            database.messageDao().insertAttachments(attachments)
        }
    }

    private fun parseHistoryMessage(obj: JsonObject): Message? {
        val role = obj["role"]?.jsonPrimitive?.content ?: return null
        // 鍚堝苟鎵€鏈?text 绫诲瀷鐨?content block锛堟敮鎸佸 block 娑堟伅锛?        val content = obj["content"]?.jsonArray?.mapNotNull { block ->
            val b = block.jsonObject
            if (b["type"]?.jsonPrimitive?.content == "text") {
                b["text"]?.jsonPrimitive?.content
            } else null
        }?.joinToString("\n") ?: ""
        val timestamp = obj["timestamp"]?.jsonPrimitive?.long ?: System.currentTimeMillis()
        val id = obj["idempotencyKey"]?.jsonPrimitive?.content
            ?: obj["__openclaw"]?.jsonObject?.get("id")?.jsonPrimitive?.content
            ?: java.util.UUID.randomUUID().toString()

        return Message(
            id = id,
            role = when (role) {
                "user" -> MessageRole.User
                "assistant" -> MessageRole.Assistant
                else -> MessageRole.System
            },
            content = content,
            timestamp = timestamp
        )
    }

    fun retryConnection() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            initialize()
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    override fun onCleared() {
        super.onCleared()
        client.disconnect()
    }
}
