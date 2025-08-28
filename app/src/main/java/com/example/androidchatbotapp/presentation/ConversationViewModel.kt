package com.example.androidchatbotapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidchatbotapp.data.AppDatabase
import com.example.androidchatbotapp.data.ConversationRepository
import com.example.androidchatbotapp.domain.Conversation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConversationViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ConversationRepository

    init {
        val db = AppDatabase.getInstance(application)
        repository = ConversationRepository(db)
    }

    fun loadAllConversations(onResult: (List<Conversation>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val conversations = repository.getAllConversations()
            withContext(Dispatchers.Main) { onResult(conversations) }
        }
    }

    fun createConversation(
        rawTitle: String,
        onSuccess: (Long, String) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val finalTitle = buildTitle(rawTitle)

            val error = validateTitle(finalTitle)
            if (error != null) {
                withContext(Dispatchers.Main) { onError(error) }
                return@launch
            }

            val id = repository.createConversation(finalTitle)
            withContext(Dispatchers.Main) { onSuccess(id, finalTitle) }
        }
    }

    private suspend fun buildTitle(raw: String): String {
        val normalized = normalizeTitle(raw)
        if (normalized.isNotEmpty()) return normalized

        // nume implicit
        val count = repository.getConversationsCount()
        return "Conversation #${count + 1}"
    }

    private fun normalizeTitle(input: String): String =
        input.trim().replace(Regex("\\s+"), " ")

    private fun validateTitle(title: String): String? {
        if (title.isBlank()) return "Title cannot be empty."
        if (title.length < 3) return "Title must have at least 3 characters."
        if (title.length > 100) return "Title cannot pass 100 characters."
        return null
    }

}
