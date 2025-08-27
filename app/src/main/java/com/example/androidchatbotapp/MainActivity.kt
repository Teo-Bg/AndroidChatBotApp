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

        messageViewModel = ViewModelProvider(this).get(MessageViewModel::class.java)

        messageViewModel.sendMessage(1L, "What do you think I should do in order to prepare for an interview for a tech job?") { aiResponse ->
            Log.d("MainActivity", aiResponse.toString())
        }

        messageViewModel.sendMessage(1L, "Tell me a joke I can say at a party.") { aiResponse ->
            Log.d("MainActivity", aiResponse.toString())
        }


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