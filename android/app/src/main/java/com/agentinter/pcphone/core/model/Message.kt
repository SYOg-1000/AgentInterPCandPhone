package com.agentinter.pcphone.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val id: String,
    val role: MessageRole,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isReasoning: Boolean = false,
    val attachments: List<Attachment> = emptyList()
)

@Serializable
enum class MessageRole {
    User, Assistant, System
}

@Serializable
data class Attachment(
    val id: String,
    val type: AttachmentType,
    val name: String,
    val url: String? = null,
    val localPath: String? = null
)

@Serializable
enum class AttachmentType {
    Image, File
}
