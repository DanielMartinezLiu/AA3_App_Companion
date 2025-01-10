package com.enti.app_companion

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
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
import com.google.firebase.database.Query
import java.sql.Struct

private lateinit var analytics: FirebaseAnalytics

class Login : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val mailText : EditText  = findViewById(R.id.mail_field)
        val passwordText : EditText = findViewById(R.id.password_field)

        analytics = FirebaseAnalytics.getInstance(this)

        val bundle = Bundle().apply {
            putString("portrait_orentation", (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE).toString())
        }

        analytics.logEvent("OpenAppSettings", bundle)

        val databaseUrl = "https://appcompanion-5f7f6-default-rtdb.europe-west1.firebasedatabase.app"
        database = FirebaseDatabase.getInstance(databaseUrl).getReference("users")

        val loginButton: Button = findViewById(R.id.login_button)
        loginButton.setOnClickListener{
            //LOGIN
            login(mailText.text.toString(), passwordText.text.toString())

        }
        val registerButton: Button = findViewById(R.id.register_button)
        registerButton.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }

    private  fun login(mailContent : String, passwordContent: String){
        //Comprobar si los datos son correctos
        val query: Query = database.orderByChild("mail").equalTo(mailContent)

        query.get()
            .addOnSuccessListener { snapshot ->
                Log.d("Login", "Query enviada")
                if (snapshot.exists()) {
                    var userFound = false
                    for (child in snapshot.children) {
                        val dbPassword = child.child("password").value.toString() // Cambia "password" al nombre exacto del campo en tu base de datos
                        if (dbPassword == passwordContent) {
                            userFound = true
                            break
                        }
                    }
                    if (userFound) {
                        Log.d("Login", "Usuario y contraseña correctos")
                        val intent = Intent(this, News::class.java)
                        startActivity(intent)
                    } else {
                        Log.d("Login", "Contraseña incorrecta")
                    }
                } else {
                    Log.d("Login", "Correo no encontrado")
                }

            }
            .addOnFailureListener { exception ->
                Log.e("Login", "Error al consultar la base de datos", exception)
            }
    }


}