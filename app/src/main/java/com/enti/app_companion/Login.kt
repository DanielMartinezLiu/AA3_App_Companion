package com.enti.app_companion

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
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

    private lateinit var playerPrefs: SharedPreferences
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

        playerPrefs = getSharedPreferences("login_prefs", Context.MODE_PRIVATE)

        val currentMail = playerPrefs.getString("mail", "")
        if (currentMail != "") {
            auth.signOut()
            navigateToNews()
            return
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
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    playerPrefs.edit().putString("mail", email).apply()
                    navigateToNews()
                } else {
                    Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signInWithGoogle() {
        // Configure Google Sign-In options
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Obtained from Firebase Console
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Start the Google Sign-In intent
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(Exception::class.java)
                if (account != null) {
                    firebaseAuthWithGoogle(account.idToken!!)
                }
            } catch (e: Exception) {
                Log.w("Login", "Google sign-in failed", e)
                Toast.makeText(this, "Google sign-in failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    playerPrefs.edit().putString("mail", user?.email).apply()
                    navigateToNews()
                } else {
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