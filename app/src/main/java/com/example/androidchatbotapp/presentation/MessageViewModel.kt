package com.example.androidchatbotapp.presentation

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidchatbotapp.data.AppDatabase
import com.example.androidchatbotapp.data.MessageRepository
import com.example.androidchatbotapp.domain.Message
import com.google.mediapipe.tasks.genai.llminference.LlmInference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("SdCardPath")
class MessageViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MessageRepository
    private var llmInference: LlmInference? = null

    private val _isModelReady = MutableLiveData(false)
    val isModelReady: LiveData<Boolean> get() = _isModelReady

    init {
        val messageDao = AppDatabase.getInstance(application).getMessageDao()
        repository = MessageRepository(messageDao)

        viewModelScope.launch(Dispatchers.IO) {
            val taskOptions = LlmInference.LlmInferenceOptions.builder()
                .setModelPath("/data/data/com.example.androidchatbotapp/LLM/gemma3-1b-it-int4.task")
                .setMaxTopK(64)
                .build()

            llmInference = LlmInference.createFromOptions(application, taskOptions)
            _isModelReady.postValue(true)
        }
    }

    fun isReady() = llmInference != null

    fun loadConversation(conversationId: Long, onResult: (List<Message>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val history = repository.getMessagesForConversation(conversationId)
            withContext(Dispatchers.Main) {
                onResult(history)
            }
        }
    }

    fun sendMessage(conversationId: Long, userText: String, onResult: (Message) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val userMessage = Message(idConversation = conversationId, sentDate = getDateString(), sender = 0, text = userText)
            repository.addMessage(userMessage)

            val inference = llmInference
            val aiResponseText = if (inference != null) {
                inference.generateResponse(userText)
            } else {
                "[Model not ready yet]"
            }

            val aiMessage = Message(idConversation = conversationId, sentDate = getDateString(), sender = 1, text = aiResponseText)
            repository.addMessage(aiMessage)

            withContext(Dispatchers.Main) {
                onResult(aiMessage)
            }
        }
    }

    private fun getDateString(): String {
        return java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault())
            .format(java.util.Date())
    }
}

    // do not touch this function

    /*

fun sendMessageWithContext(conversationId: Long, userText: String, onResult: (List<Message>) -> Unit) {
    viewModelScope.launch(Dispatchers.IO) {
        val userMessage = Message(
            idConversation = conversationId,
            sentDate = getNowAsString(),
            sender = 0,
            text = userText
        )
        repository.addMessage(userMessage)

        val history = repository.getMessagesForConversation(conversationId)
        val context = history.joinToString("\n") {
            if (it.sender == 0) "User: ${it.text}" else "AI: ${it.text}"
        }

        val aiResponse = llmInference.generateResponse(context)

        val aiMessage = Message(
            idConversation = conversationId,
            sentDate = getNowAsString(),
            sender = 1,
            text = aiResponse
        )
        repository.addMessage(aiMessage)

        val updatedHistory = repository.getMessagesForConversation(conversationId)
        withContext(Dispatchers.Main) {
            onResult(updatedHistory)
        }
    }
}

 */
