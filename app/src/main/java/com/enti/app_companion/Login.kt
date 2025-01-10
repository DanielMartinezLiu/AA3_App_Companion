package com.enti.app_companion

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import java.sql.Struct

private lateinit var analytics: FirebaseAnalytics

class Login : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var playerPrefs: SharedPreferences
    private lateinit var mailText : EditText
    private lateinit var passwordText : EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private  lateinit var bundle: Bundle
    private val databaseUrl = "https://appcompanion-5f7f6-default-rtdb.europe-west1.firebasedatabase.app"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        playerPrefs = getSharedPreferences("login_prefs", Context.MODE_PRIVATE)

        val currentMail = playerPrefs.getString("mail", "")
        Log.d("Login", "El mail guardado es ${currentMail}")
        if(currentMail != "")
        {
            Log.d("Login", "Ya esta logueado")
            val intent = Intent(this, News::class.java)
            startActivity(intent)
            return
        }

        mailText  = findViewById(R.id.mail_field)
        passwordText = findViewById(R.id.password_field)

        analytics = FirebaseAnalytics.getInstance(this)

        bundle = Bundle().apply {
            putString("portrait_orentation", (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE).toString())
        }

        analytics.logEvent("OpenAppSettings", bundle)

        database = FirebaseDatabase.getInstance(databaseUrl).getReference("users")

        loginButton = findViewById(R.id.login_button)
        loginButton.setOnClickListener{
            //LOGIN
            login(mailText.text.toString(), passwordText.text.toString())
        }

        registerButton = findViewById(R.id.register_button)
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
                        playerPrefs.edit().putString("mail", mailContent).commit()
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