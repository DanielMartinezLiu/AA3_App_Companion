package com.enti.app_companion

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private lateinit var analytics: FirebaseAnalytics

class Login : AppCompatActivity() {

    private  lateinit var mailText : EditText
    private  lateinit var userText : EditText
    private  lateinit var passwordText : EditText
    private  lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mailText = findViewById(R.id.mail_field)
        userText = findViewById(R.id.username_field)
        passwordText = findViewById(R.id.password_field)

        analytics = FirebaseAnalytics.getInstance(this)

        analytics.logEvent("MyFirstEvent", null)

        val bundle = Bundle().apply {
            putString("portrait_orentation", (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE).toString())
        }

        analytics.logEvent("OpenAppSettings", bundle)

        val databaseUrl = "https://appcompanion-5f7f6-default-rtdb.europe-west1.firebasedatabase.app"
        database = FirebaseDatabase.getInstance(databaseUrl).getReference("messages")
        val dataId = database.push().key

        val loginButton: Button = findViewById(R.id.login_button)
        loginButton.setOnClickListener{
            //Enviar los datos de la base de datos
            val messageData = mapOf(
                "user" to "Jose",
                "message" to "me cago en to sus muertos"
            )

            if(dataId != null)
            {
                database.child(dataId).setValue(messageData)
                    .addOnSuccessListener { result ->
                        Log.d("Firebase Test", "Insert correcto")
                    }
                    .addOnFailureListener{ exception ->
                        Log.d("Firebase Test", "Error: ${exception.message}")
                    }
            }
        }
        val registerButton: Button = findViewById(R.id.register_button)
        registerButton.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }





    }
}