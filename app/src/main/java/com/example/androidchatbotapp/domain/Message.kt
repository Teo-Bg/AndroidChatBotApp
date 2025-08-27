package com.example.androidchatbotapp.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "messages",
    foreignKeys = [
        ForeignKey(
            entity = Conversation::class,
            parentColumns = ["id"],
            childColumns = ["id_conversation"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "id_conversation", index = true) val idConversation: Long,
    @ColumnInfo(name = "sent_date") val sentDate: String,
    @ColumnInfo(name = "sender") val sender: Int, //0 - pt user si 1 - pt chatbot
    @ColumnInfo(name = "text") val text: String
)