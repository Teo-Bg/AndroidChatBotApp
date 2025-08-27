package com.example.androidchatbotapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.androidchatbotapp.domain.Conversation
import com.example.androidchatbotapp.presentation.MessageViewModel
import com.example.androidchatbotapp.ui.theme.AndroidChatbotAppTheme
import com.google.mediapipe.tasks.genai.llminference.LlmInference

class MainActivity : ComponentActivity() {

    private lateinit var messageViewModel: MessageViewModel

    @SuppressLint("SdCardPath")
    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidChatbotAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }



        // Set the configuration options for the LLM Inference task
        //val taskOptions = LlmInference.LlmInferenceOptions.builder()
            //.setModelPath("/data/local/tmp/LLM/gemma3-1b-it-int4.task")
            //.setModelPath("/data/data/com.example.androidchatbotapp/LLM/gemma3-1b-it-int4.task")
            //.setMaxTopK(64)
            //.build()

// Create an instance of the LLM Inference task
        //val llmInference = LlmInference.createFromOptions(this, taskOptions)



        messageViewModel = ViewModelProvider(this).get(MessageViewModel::class.java)

        messageViewModel.sendMessage(1L, "What do you think I should do in order to prepare for an interview for a tech job?") { aiResponse ->
            Log.d("MainActivity", aiResponse.toString())
        }

        messageViewModel.sendMessage(1L, "Tell me a joke I can say at a party.") { aiResponse ->
            Log.d("MainActivity", aiResponse.toString())
        }

        //val response = llmInference.generateResponse("What do you think I should do in order to prepare for an interview for a tech job?")
       // Log.d("MainActivity", response.toString())


       // val conversation = Conversation(1, "Hello")
       // Log.d("MainActivity", conversation.toString())

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidChatbotAppTheme {
        Greeting("Android")
    }
}