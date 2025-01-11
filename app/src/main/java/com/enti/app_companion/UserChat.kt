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

    private fun setupChat()
    {
        chatRecyclerView = findViewById(R.id.user_chat_recycle_view)
        confirmButton = findViewById(R.id.confirm_text_button)
        inputText = findViewById(R.id.input_text_field)


        messages = mutableListOf()
        chatAdapter = ChatUserAdapter(messages)
        chatRecyclerView = findViewById(R.id.user_chat_recycle_view)
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = chatAdapter
    }

    private fun sendMessage() {
        confirmButton.setOnClickListener {
            val message : String = inputText.text.toString()
            if (message.isNotBlank()) {
                messages.add("You: $message")
                chatAdapter.notifyItemInserted(messages.size - 1)

                chatRecyclerView.scrollToPosition(messages.size - 1)

                inputText.text.clear()

                simulateResponse()
            }
        }
    }

    private fun simulateResponse() {
        chatRecyclerView.postDelayed({
            messages.add("Other: OK")
            chatAdapter.notifyItemInserted(messages.size - 1)
            chatRecyclerView.scrollToPosition(messages.size - 1)
        }, 1000)
    }
}