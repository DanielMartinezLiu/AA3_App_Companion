package com.enti.app_companion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class Login : AppCompatActivity() {

    private lateinit var mailText: EditText
    private lateinit var passwordText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var googleSignInButton: com.google.android.gms.common.SignInButton

    private lateinit var auth: FirebaseAuth
    private lateinit var analytics: FirebaseAnalytics

    private val RC_SIGN_IN = 9001 // Request code for Google Sign-In

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        analytics = FirebaseAnalytics.getInstance(this)

        // Verifica si hay un usuario autenticado
        if (auth.currentUser != null) {
            Log.d("Login", "Hay user, este user es ${auth.currentUser}" )
            navigateToNews()
            finish()
        }

        mailText = findViewById(R.id.mail_field)
        passwordText = findViewById(R.id.password_field)
        loginButton = findViewById(R.id.login_button)
        registerButton = findViewById(R.id.register_button)
        googleSignInButton = findViewById(R.id.google_sign_in_button)

        loginButton.setOnClickListener {
            loginWithEmail(mailText.text.toString(), passwordText.text.toString())
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        googleSignInButton.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun loginWithEmail(email: String, password: String) {
        // Utiliza Firebase Authentication para iniciar sesión con correo y contraseña.
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Si el inicio de sesión es exitoso, navega a la pantalla principal.
                    navigateToNews()
                } else {
                    // Si falla, muestra un mensaje de error al usuario con detalles de la excepción.
                    Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signInWithGoogle() {
        // Configura las opciones para el inicio de sesión con Google.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Obtén el ID de cliente desde Firebase Console.
            .requestEmail() // Solicita el correo electrónico del usuario.
            .build()

        // Crea un cliente de inicio de sesión de Google con las opciones configuradas.
        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Inicia la actividad de inicio de sesión de Google.
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN) // Usa un código de solicitud para manejar el resultado.
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Verifica si el resultado corresponde al inicio de sesión con Google.
        if (requestCode == RC_SIGN_IN) {
            // Obtén el resultado de la actividad de inicio de sesión.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Intenta obtener la cuenta de Google desde el resultado.
                val account = task.getResult(Exception::class.java)
                if (account != null) {
                    // Si la cuenta es válida, autentica con Firebase usando el ID Token.
                    firebaseAuthWithGoogle(account.idToken!!)
                }
            } catch (e: Exception) {
                // Si ocurre un error, registra el fallo y muestra un mensaje al usuario.
                Log.w("Login", "Google sign-in failed", e)
                Toast.makeText(this, "Google sign-in failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        // Crea las credenciales de Firebase utilizando el ID Token proporcionado.
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        // Autentica al usuario con las credenciales en Firebase.
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Si la autenticación es exitosa, navega a la pantalla principal.
                    navigateToNews()
                } else {
                    // Si ocurre un error, muestra un mensaje al usuario con detalles de la excepción.
                    Toast.makeText(this, "Authentication Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun navigateToNews() {
        val intent = Intent(this, News::class.java)
        startActivity(intent)
        finish()
    }
}
