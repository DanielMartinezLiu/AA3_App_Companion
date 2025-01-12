package com.enti.app_companion

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import models.ChatUserAdapter
import models.ChatUserModel
import models.RecordAdapter
import models.RecordModel

class UserChat : AppCompatActivity() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var confirmButton: Button
    private lateinit var inputText: EditText
    private lateinit var messages: MutableList<String>
    private lateinit var chatAdapter: ChatUserAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_chat)

        setupHeader()
        setupChat()
        sendMessage()

        val image : ImageView = findViewById(R.id.chat_image)
        image.setImageResource(intent.getIntExtra("chat_image", R.drawable.jokers))

        val username : TextView = findViewById(R.id.username)
        username.text = intent.getStringExtra("chat_username")
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

    private fun setupChat() {
        // Inicializa y configura el RecyclerView para mostrar los mensajes del chat
        chatRecyclerView = findViewById(R.id.user_chat_recycle_view)
        // Asocia el botón de confirmación de texto
        confirmButton = findViewById(R.id.confirm_text_button)
        // Asocia el campo de texto para entrada de mensajes
        inputText = findViewById(R.id.input_text_field)

        // Inicializa la lista de mensajes como una lista mutable vacía
        messages = mutableListOf()
        // Crea una instancia del adaptador para el chat, utilizando la lista de mensajes
        chatAdapter = ChatUserAdapter(messages)
        // Configura el RecyclerView con un layout manager para una lista vertical
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        // Asigna el adaptador al RecyclerView
        chatRecyclerView.adapter = chatAdapter
    }

    private fun sendMessage() {
        // Configura el botón para enviar mensajes cuando se haga clic
        confirmButton.setOnClickListener {
            // Obtiene el texto ingresado en el campo de entrada
            val message: String = inputText.text.toString()
            // Verifica que el mensaje no esté vacío o solo contenga espacios en blanco
            if (message.isNotBlank()) {
                // Agrega el mensaje del usuario a la lista de mensajes
                messages.add("You: $message")
                // Notifica al adaptador que se ha añadido un nuevo elemento
                chatAdapter.notifyItemInserted(messages.size - 1)

                // Desplaza el RecyclerView al último mensaje
                chatRecyclerView.scrollToPosition(messages.size - 1)

                // Limpia el campo de texto de entrada
                inputText.text.clear()

                // Simula una respuesta automática
                simulateResponse()
            }
        }
    }

    private fun simulateResponse() {
        // Simula una respuesta después de un retraso de 1 segundo
        chatRecyclerView.postDelayed({
            // Agrega una respuesta genérica a la lista de mensajes
            messages.add("Other: OK")
            // Notifica al adaptador que se ha añadido un nuevo elemento
            chatAdapter.notifyItemInserted(messages.size - 1)
            // Desplaza el RecyclerView al último mensaje
            chatRecyclerView.scrollToPosition(messages.size - 1)
        }, 1000)
    }
}