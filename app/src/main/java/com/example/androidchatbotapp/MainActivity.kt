package com.example.androidchatbotapp

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
import com.example.androidchatbotapp.domain.Conversation
import com.example.androidchatbotapp.ui.theme.AndroidChatbotAppTheme
import com.google.mediapipe.tasks.genai.llminference.LlmInference

class MainActivity : ComponentActivity() {


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

        /*
        // Set the configuration options for the LLM Inference task
        val taskOptions = LlmInference.LlmInferenceOptions.builder()
            .setModelPath("/data/local/tmp/LLM/gemma3-1b-it-int4.task")
            .setMaxTopK(64)
            .build()

// Create an instance of the LLM Inference task
        val llmInference = LlmInference.createFromOptions(this, taskOptions)

        val result = llmInference.generateResponse("Hello world!")
        Log.d("a", "result: $result")

         */

        val conversation = Conversation(1, "Hello")
        Log.d("MainActivity", conversation.toString())

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