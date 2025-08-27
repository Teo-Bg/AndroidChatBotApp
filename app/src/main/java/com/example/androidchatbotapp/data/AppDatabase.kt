package com.example.androidchatbotapp.data

import android.content.Context
import androidx.room.Database
import com.example.androidchatbotapp.domain.Conversation
import com.example.androidchatbotapp.domain.Message
import androidx.room.RoomDatabase
import androidx.room.Room

@Database(entities = [Conversation::class, Message::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance

            }
        }
    }

    abstract fun getConversationDao(): ConversationDao
}