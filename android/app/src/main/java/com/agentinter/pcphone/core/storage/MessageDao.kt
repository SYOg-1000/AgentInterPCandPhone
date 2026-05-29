package com.agentinter.pcphone.core.storage

import androidx.room.*

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttachments(attachments: List<AttachmentEntity>)

    @Transaction
    @Query("SELECT * FROM messages WHERE session_key = :sessionKey ORDER BY timestamp ASC LIMIT :limit")
    suspend fun getMessagesWithAttachments(sessionKey: String, limit: Int = 100): List<MessageWithAttachments>

    @Query("SELECT * FROM messages WHERE session_key = :sessionKey ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastMessage(sessionKey: String): MessageEntity?

    @Query("DELETE FROM messages WHERE session_key = :sessionKey")
    suspend fun clearSession(sessionKey: String)

    @Query("DELETE FROM messages WHERE timestamp < :before")
    suspend fun deleteOlderThan(before: Long)
}
