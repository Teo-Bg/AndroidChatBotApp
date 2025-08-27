package com.example.androidchatbotapp.data

import com.example.androidchatbotapp.domain.Message

class MessageRepository(private val messageDao: MessageDao) {

    suspend fun addMessage(message: Message): Long {
        return messageDao.insertMessage(message)
    }

    suspend fun getMessagesForConversation(conversationId: Long): List<Message> {
        return messageDao.getMessagesForConversation(conversationId)
    }
}