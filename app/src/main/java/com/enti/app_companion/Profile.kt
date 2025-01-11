package com.enti.app_companion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth


class Profile : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()

        setupHeader();

        logoutButton = findViewById(R.id.logout_button)

        logoutButton.setOnClickListener{
            auth.signOut()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        val apiButton = findViewById<Button>(R.id.api_button)
        apiButton.setOnClickListener{
            val intent = Intent(this, Api::class.java)
            startActivity(intent)
        }

        setupHeader()
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
            findViewById(R.id.header_button_3)
        )
    }


}