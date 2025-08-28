package com.example.androidchatbotapp.data

import com.example.androidchatbotapp.domain.Conversation

class ConversationRepository(private val database: AppDatabase) {

    suspend fun createConversation(title: String): Long {
        val conversation = Conversation(title = title)
        return database.getConversationDao().insert(conversation)
    }

    suspend fun getAllConversations(): List<Conversation> {
        return database.getConversationDao().getAll()
    }

    suspend fun getConversationsCount(): Int {
        return database.getConversationDao().count()
    }
}