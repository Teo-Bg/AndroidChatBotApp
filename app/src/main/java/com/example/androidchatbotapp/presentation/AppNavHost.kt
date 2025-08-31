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
            ) }
        composable("chat/{conversationId}") { backStackEntry ->
            val convId = backStackEntry.arguments?.getString("conversationId")?.toLongOrNull()
            if (convId != null) {
                ChatScreen(
                    conversationId = convId,
                    messageViewModel = messageViewModel,
                    onBack = { navController.popBackStack()}
                )
            } else {
                //daca id invalid, ne intoarcem in lista
                navController.popBackStack()
            }
        }
    }
}
