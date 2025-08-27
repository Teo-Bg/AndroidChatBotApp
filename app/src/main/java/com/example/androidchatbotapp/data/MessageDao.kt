package com.example.androidchatbotapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidchatbotapp.domain.Message

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: Message): Long

    @Query("SELECT * FROM messages WHERE id_conversation = :conversationId")
    suspend fun getMessagesForConversation(conversationId: Long): List<Message>
}