package com.example.androidchatbotapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.androidchatbotapp.domain.Conversation

@Dao
interface ConversationDao {

    @Query("SELECT * FROM conversations")
    suspend fun getAll(): List<Conversation>

    @Query("SELECT COUNT(*) FROM conversations")
    suspend fun count(): Int

    @Insert
    suspend fun insert(conversation: Conversation): Long
}