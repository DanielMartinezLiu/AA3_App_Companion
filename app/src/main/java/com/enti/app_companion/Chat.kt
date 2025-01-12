package com.enti.app_companion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import models.ChatAdapter
import models.ChatModel

class Chat : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat)

        setupHeader()

        // Lista de chats predefinidos con imágenes y nombres
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

        // Configuración del RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.chat_recycle_view)
        recyclerView.layoutManager = LinearLayoutManager(this) // Disposición lineal de elementos

        // Asigna un adaptador al RecyclerView para mostrar la lista de chats
        recyclerView.adapter = ChatAdapter(chats) { chat ->
            // Maneja el clic en un elemento de la lista
            val intent: Intent = Intent(this, UserChat::class.java).apply {
                putExtra("chat_image", chat.imageResId) // Pasa la imagen del chat al intent
                putExtra("chat_username", chat.username) // Pasa el nombre del chat al intent
            }
            startActivity(intent)
        }
    }

    // Configura los botones del encabezado y sus destinos
    private fun setupHeader() {
        HeaderUtils.setupHeader(
            this,
            mapOf(
                findViewById<Button>(R.id.header_button_1) to News::class.java,
                findViewById<Button>(R.id.header_button_2) to Chat::class.java,
                findViewById<Button>(R.id.header_button_3) to Profile::class.java,
                findViewById<Button>(R.id.header_button_4) to Jokers::class.java,
                findViewById<Button>(R.id.header_button_5) to Record::class.java
            ),
            findViewById(R.id.header_button_2) // Indica el botón actualmente seleccionado
        )
    }
}
