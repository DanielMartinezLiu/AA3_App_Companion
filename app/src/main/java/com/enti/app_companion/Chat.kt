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
            ChatModel(R.drawable.abandoned_deck, "Abandoned Deck", "New Text"),
            ChatModel(R.drawable.black_deck, "Black Deck", "New Text"),
            ChatModel(R.drawable.erratic_deck, "Erratic Deck", "New Text"),
            ChatModel(R.drawable.challenge_deck, "Challenge Deck", "New Text"),
            ChatModel(R.drawable.green_deck, "Green Deck", "New Text"),
            ChatModel(R.drawable.red_deck, "Red Deck", "New Text"),
            ChatModel(R.drawable.magic_deck, "Magic Deck", "New Text"),
            ChatModel(R.drawable.zodiac_deck, "Zodiac Deck", "New Text"),
        )

        val recyclerView : RecyclerView = findViewById(R.id.chat_recycle_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = ChatAdapter(chats) { chat ->
            val intent : Intent = Intent(this, UserChat::class.java)
            intent.putExtra("chat_image", chat.imageResId)
            intent.putExtra("chat_username", chat.username)
            startActivity(intent)
        }
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