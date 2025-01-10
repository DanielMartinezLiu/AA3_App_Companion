package com.enti.app_companion

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var playerPrefs: SharedPreferences
    private lateinit var mailText: EditText
    private lateinit var passwordText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var analytics: FirebaseAnalytics
    private lateinit var bundle: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        playerPrefs = getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
        auth = FirebaseAuth.getInstance() // Inicializar FirebaseAuth

        val currentMail = playerPrefs.getString("mail", "")
        Log.d("Login", "El mail guardado es $currentMail")
        if (auth.currentUser != null) {
            Log.d("Login", "Ya está logueado")
            navigateToNews()
            return
        }

        mailText = findViewById(R.id.mail_field)
        passwordText = findViewById(R.id.password_field)

        analytics = FirebaseAnalytics.getInstance(this)

        bundle = Bundle().apply {
            putString(
                "portrait_orientation",
                (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE).toString()
            )
        }

        analytics.logEvent("OpenAppSettings", bundle)

        loginButton = findViewById(R.id.login_button)
        loginButton.setOnClickListener {
            val mail = mailText.text.toString()
            val password = passwordText.text.toString()

            // Llama al método de inicio de sesión con correo y contraseña
            loginWithEmail(mail, password)
        }

        registerButton = findViewById(R.id.register_button)
        registerButton.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }

    private fun loginWithEmail(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Log.d("Login", "Email o contraseña vacíos")
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("Login", "Inicio de sesión exitoso")
                    playerPrefs.edit().putString("mail", email).apply() // Guardar el correo en SharedPreferences
                    navigateToNews()
                } else {
                    Log.w("Login", "Error al iniciar sesión", task.exception)
                }
            }
    }

    private fun navigateToNews() {
        val intent = Intent(this, News::class.java)
        startActivity(intent)
        finish() // Opcional: cierra la pantalla de inicio de sesión para evitar que el usuario regrese
    }
}