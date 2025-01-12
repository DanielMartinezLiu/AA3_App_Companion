package com.enti.app_companion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Register : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private val databaseUrl = "https://appcompanion-5f7f6-default-rtdb.europe-west1.firebasedatabase.app"
    private lateinit var auth: FirebaseAuth // FirebaseAuth para manejo de usuarios
    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val mailText: EditText = findViewById(R.id.mail_field)
        val userText: EditText = findViewById(R.id.username_field)
        val passwordText: EditText = findViewById(R.id.password_field)
        val repeatedPasswordText: EditText = findViewById(R.id.repeat_password_field)

        database = FirebaseDatabase.getInstance(databaseUrl).getReference("users")
        auth = FirebaseAuth.getInstance() // Inicializar FirebaseAuth
        analytics = FirebaseAnalytics.getInstance(this)

        val loginButton: Button = findViewById(R.id.login_button)
        loginButton.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        val registerButton: Button = findViewById(R.id.register_button)
        registerButton.setOnClickListener {
            val mailContent = mailText.text.toString()
            val userContent = userText.text.toString()
            val passwordContent = passwordText.text.toString()
            val repeatedPasswordContent = repeatedPasswordText.text.toString()

            // Validar que las contraseñas coincidan
            if (passwordContent != repeatedPasswordContent) {
                Log.d("Register", "Las contraseñas no coinciden")
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validar que el correo y la contraseña no estén vacíos
            if (mailContent.isEmpty() || passwordContent.isEmpty() || userContent.isEmpty()) {
                Toast.makeText(this, "El correo y la contraseña no pueden estar vacíos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Registrar usuario en Firebase Authentication
            registerWithEmail(mailContent, passwordContent, userContent)
        }
    }

    private fun registerWithEmail(email: String, password: String, username: String) {
        // Crea un usuario en Firebase Authentication con correo electrónico y contraseña
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("Register", "Usuario registrado con éxito")

                    // Guarda información adicional del usuario en la base de datos
                    saveUserToDatabase(email, username)

                    // Registra un evento de éxito en Firebase Analytics
                    val bundle = Bundle()
                    bundle.putString(FirebaseAnalytics.Param.METHOD, "Sing up succes: $email")
                    analytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle)

                    // Redirige al usuario a la pantalla de inicio de sesión
                    val intent = Intent(this, News::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Maneja errores de registro
                    Log.e("Register", "Error al registrar el usuario", task.exception)
                    Toast.makeText(this, "Error al registrar: ${task.exception?.message}", Toast.LENGTH_SHORT).show()

                    // Registra un evento de error en Firebase Analytics
                    val bundle = Bundle()
                    bundle.putString(FirebaseAnalytics.Param.METHOD, "Sing Up failed: ${task.exception?.message}")
                    analytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle)
                }
            }
    }

    private fun saveUserToDatabase(email: String, username: String) {
        // Obtiene el UID del usuario actual
        val userId = auth.currentUser?.uid
        if (userId != null) {
            // Crea un mapa con los datos del usuario para guardar en la base de datos
            val userData = mapOf(
                "id" to userId,
                "mail" to email,
                "user" to username
            )

            // Guarda los datos del usuario en la base de datos bajo su UID
            database.child(userId).setValue(userData)
                .addOnSuccessListener {
                    Log.d("Register", "Información del usuario guardada en la base de datos")
                }
                .addOnFailureListener { exception ->
                    // Maneja errores al guardar datos en la base de datos
                    Log.e("Register", "Error al guardar información del usuario", exception)
                }
        }
    }

}