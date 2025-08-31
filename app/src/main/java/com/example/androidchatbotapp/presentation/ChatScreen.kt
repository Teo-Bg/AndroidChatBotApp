package com.example.androidchatbotapp.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.androidchatbotapp.domain.Message

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    conversationId: Long,
    messageViewModel: MessageViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    //lista mesaje
    var messages by remember { mutableStateOf<List<Message>>(emptyList()) }
    //input text
    var input by remember { mutableStateOf(TextFieldValue("")) }
    //scroll state
    val listState = rememberLazyListState()

    //incarcam istoricul cand intri
    LaunchedEffect(conversationId) {
        messageViewModel.loadConversation(conversationId) { history ->
            messages = history
        }
    }

    //scroll la ultimul msj
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.lastIndex)
    }

    Scaffold(
        topBar = {
            //bara de sus care are button de back
            CenterAlignedTopAppBar(
                title = { Text("Chat") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            //input + button send
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .navigationBarsPadding()
                    .imePadding()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    placeholder = { Text("Type a message…") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            //trimite mesaj cand apesi send
                            sendMessage(
                                conversationId = conversationId,
                                text = input.text,
                                vm = messageViewModel,
                                onEmpty = {
                                    Toast.makeText(context, "Message is empty", Toast.LENGTH_SHORT).show()
                                },
                                onSent = {
                                    messageViewModel.loadConversation(conversationId) { history -> messages = history }
                                    input = TextFieldValue("")
                                }
                            )
                        }
                    )
                )
                Button(
                    onClick = {
                        //trimite msj cand apesi buton
                        sendMessage(
                            conversationId = conversationId,
                            text = input.text,
                            vm = messageViewModel,
                            onEmpty = {
                                Toast.makeText(context, "Message is empty", Toast.LENGTH_SHORT).show()
                            },
                            onSent = {
                                messageViewModel.loadConversation(conversationId) { history -> messages = history }
                                input = TextFieldValue("")
                            }
                        )
                    }
                ) { Text("Send") }
            }
        }
    ) { padding ->
        //list de mesaje
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .imePadding(),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { msg ->
                MessageBubble(
                    isUser = msg.sender == 0,
                    text = msg.text,
                    timestamp = msg.sentDate
                )
            }
        }
    }
}

@Composable
private fun MessageBubble(
    isUser: Boolean,
    text: String,
    timestamp: String
) {
    //culori bule user vs bot
    val bubbleColor = if (isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    val textColor = if (isUser) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant

    //aliniere in functie de sender
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 320.dp)
                .background(bubbleColor, shape = MaterialTheme.shapes.large)
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Text(text = text, style = MaterialTheme.typography.bodyLarge, color = textColor)
            Spacer(Modifier.height(4.dp))
            Text(
                text = timestamp,
                style = MaterialTheme.typography.labelLarge,
                color = textColor.copy(alpha = 0.8f)
            )
        }
    }
}

//functie pt trimis mesaj
private fun sendMessage(
    conversationId: Long,
    text: String,
    vm: MessageViewModel,
    onEmpty: () -> Unit,
    onSent: (Message) -> Unit
) {
    if (text.isBlank()) {
        onEmpty(); return
    }
    vm.sendMessage(conversationId, text) { aiMsg -> onSent(aiMsg) }
}
