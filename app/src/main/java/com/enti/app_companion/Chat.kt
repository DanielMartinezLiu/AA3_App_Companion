package com.enti.app_companion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import models.ChatAdapter
import models.ChatModel
import models.RecordAdapter
import models.RecordModel

class Chat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat)

        setupHeader();

        val chats = listOf(
            ChatModel(R.drawable.jokers, "Test", "New Text"),
            ChatModel(R.drawable.jokers, "Test", "New Text"),
            ChatModel(R.drawable.jokers, "Test", "New Text"),
            ChatModel(R.drawable.jokers, "Test", "New Text"),
            ChatModel(R.drawable.jokers, "Test", "New Text"),
            ChatModel(R.drawable.jokers, "Test", "New Text"),
            ChatModel(R.drawable.jokers, "Test", "New Text"),
            ChatModel(R.drawable.jokers, "Test", "New Text"),
        )

        val recyclerView : RecyclerView = findViewById(R.id.chat_recycle_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ChatAdapter(chats)
    }

    private fun setupHeader()
    {
        HeaderUtils.setupHeader(
            this,
            mapOf(
                findViewById<Button>(R.id.header_button_1) to News::class.java,
                findViewById<Button>(R.id.header_button_2) to Chat::class.java,
                findViewById<Button>(R.id.header_button_3) to Profile::class.java,
                findViewById<Button>(R.id.header_button_4) to Jokers::class.java,
                findViewById<Button>(R.id.header_button_5) to Record::class.java
            ),
            findViewById(R.id.header_button_2)
        )
    }
}