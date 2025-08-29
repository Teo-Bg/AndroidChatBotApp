package com.example.androidchatbotapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.androidchatbotapp.presentation.ConversationViewModel
import com.example.androidchatbotapp.presentation.MessageViewModel
import com.example.androidchatbotapp.ui.theme.AppTheme
import androidx.navigation.compose.rememberNavController
import com.example.androidchatbotapp.presentation.AppNavHost


class MainActivity : ComponentActivity() {

    //val modelFile = prepareModel(this)

    //private lateinit var messageViewModel: MessageViewModel

    private val conversationViewModel: ConversationViewModel by viewModels()
    private val messageViewModel: MessageViewModel by viewModels()



    @SuppressLint("SdCardPath")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavHost(
                        navController = navController,
                        conversationViewModel = conversationViewModel,
                        messageViewModel = messageViewModel
                    )
                }
            }
        }

        // dati pe logcat filtru E2E_TEST
        runRoomE2ETest()

//        messageViewModel = ViewModelProvider(this).get(MessageViewModel::class.java)
//
//        messageViewModel.sendMessage(1L, "What do you think I should do in order to prepare for an interview for a tech job?") { aiResponse ->
//            Log.d("MainActivity", aiResponse.toString())
//        }
//
//        messageViewModel.sendMessage(1L, "Tell me a joke I can say at a party.") { aiResponse ->
//            Log.d("MainActivity", aiResponse.toString())
//        }


    }
    private fun runRoomE2ETest() {
        val TAG = "E2E_TEST"

        // 1) convo cu titlu gol / titlu dat
        conversationViewModel.createConversation(
            //rawTitle = "",
            rawTitle = "My first convo",
            onSuccess = { conversationId, finalTitle ->
                Log.d(TAG, "Conversation created: id=$conversationId, title=\"$finalTitle\"")

                // 2) mesajul userului
                messageViewModel.sendMessage(
                    conversationId = conversationId,
                    userText = "Hello, how are you?"
                ) { aiMsg ->
                    Log.d(TAG, "AI response: $aiMsg")

                    // incarcare istoric mesaje
                    messageViewModel.loadConversation(conversationId) { history ->
                        Log.d(TAG, "Message history (${history.size}):")
                        history.forEachIndexed { index, m ->
                            Log.d(
                                TAG,
                                "#$index | id=${m.id} | conv=${m.idConversation} | sender=${m.sender} | date=${m.sentDate} | text=${m.text}"
                            )
                        }
                    }
                }
            },
            onError = { err ->
                Log.e(TAG, "Create conversation error: $err")
            }
        )

        // incarcare istoric conversatii
        conversationViewModel.loadAllConversations { all ->
            Log.d(TAG, "Conversation history: ${all.size}")
            all.forEach { Log.d(TAG, " - id=${it.id}, title=\"${it.title}\"") }
        }
    }

}


// helper pt com.example.androidchatbotapp pt copierea modelului din assets
//private fun prepareModel(context: Context): File {
//    val target = File("/data/data/${context.packageName}/LLM/gemma3-1b-it-int4.task")
//    if (!target.exists()) {
//        target.parentFile?.mkdirs()
//        context.assets.open("gemma3-1b-it-int4.task").use { input ->
//            target.outputStream().use { output -> input.copyTo(output) }
//        }
//    }
//    Log.d("MainActivity", "Model at: ${target.absolutePath} (exists=${target.exists()}, size=${target.length()})")
//    return target
//}