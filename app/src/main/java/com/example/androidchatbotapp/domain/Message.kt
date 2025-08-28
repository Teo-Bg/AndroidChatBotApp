package com.example.androidchatbotapp.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "messages",
    foreignKeys = [
        ForeignKey(
            entity = Conversation::class,
            parentColumns = ["id"],
            childColumns = ["id_conversation"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["id_conversation"])]
)
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "id_conversation") val idConversation: Long,
    @ColumnInfo(name = "sent_date") val sentDate: String,
    @ColumnInfo(name = "sender") val sender: Int, //0 - pt user si 1 - pt chatbot
    @ColumnInfo(name = "text") val text: String
)