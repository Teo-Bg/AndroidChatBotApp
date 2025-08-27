package com.example.androidchatbotapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.androidchatbotapp.domain.Conversation

@Dao
interface ConversationDao {

    @Query("SELECT * FROM conversations")
    suspend fun getAll(): List<Conversation>

    @Insert
    suspend fun insert(conversation: Conversation): Long
}