package com.example.androidchatbotapp.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavHost(
    navController: NavHostController,
    conversationViewModel: ConversationViewModel,
    messageViewModel: MessageViewModel
) {
    NavHost(navController = navController, startDestination = "conversations") {
        composable("conversations") {
            ConversationListScreen(
                onConversationClick = { convId ->
                    navController.navigate("chat/$convId")
                },
                viewModel = conversationViewModel
            )
        }
        // ChatScreen vine aici dupa
    }
}
