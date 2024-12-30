package com.enti.app_companion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Record : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_record)
        setupHeader();
    }

    private fun setupHeader()
    {
        val headerButton1: Button = findViewById(R.id.header_button_1)
        val headerButton2: Button = findViewById(R.id.header_button_2)
        val headerButton3: Button = findViewById(R.id.header_button_3)
        val headerButton4: Button = findViewById(R.id.header_button_4)
        val headerButton5: Button = findViewById(R.id.header_button_5)

        setupButton(headerButton1, News::class.java)
        setupButton(headerButton2, Chat::class.java)
        setupButton(headerButton3, Profile::class.java)
        setupButton(headerButton4, Jokers::class.java)
        setupButton(headerButton5, Record::class.java)
    }
    private fun setupButton(button: Button, targetActivity: Class<*>) {
        button.setOnClickListener {
            val intent = Intent(this, targetActivity)
            startActivity(intent)
        }
    }
}