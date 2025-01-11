package com.enti.app_companion

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query


class Profile : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private val databaseUrl = "https://appcompanion-5f7f6-default-rtdb.europe-west1.firebasedatabase.app"


    private lateinit var logoutButton: ImageButton
    private  lateinit var usernameText: EditText
    private  lateinit var confirmUsernameButton: Button
    private  val isMail = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        setupHeader();

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance(databaseUrl).getReference("users")

        logoutButton = findViewById(R.id.logout_button)
        logoutButton.setOnClickListener{
            auth.signOut()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        usernameText = findViewById(R.id.username_text)
        val userId : String? = auth.currentUser?.uid // Obtener el UID del usuario actual

        if(isMail){

        }else{
            loadMailUserData(userId)
        }


        confirmUsernameButton = findViewById(R.id.confirm_username_button)

        confirmUsernameButton.setOnClickListener{
            if(isMail){
                Toast.makeText(this, "No se puede modificar un usuario de una cuenta con mail", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //Guardar info
            updateUsername(userId)
        }

        val apiButton = findViewById<ImageButton>(R.id.api_button)
        apiButton.setOnClickListener{
            val intent = Intent(this, Api::class.java)
            startActivity(intent)
        }

        setupHeader()
    }

    private fun setupHeader() {
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

    private  fun  loadMailUserData(userId: String?){
        if(userId == null){
            Toast.makeText(this, "Invalid user", Toast.LENGTH_SHORT).show()
            return
        }

        val query: Query = database.orderByChild("id").equalTo(userId)

        query.get()
            .addOnSuccessListener { snapshot ->
                Log.d("Profile Username", "Query enviada")
                if(snapshot.exists()){
                    Log.d("Profile Username", "Hay datos")
                    for(dataSnapshot in snapshot.children){
                        val username = dataSnapshot.child("user").getValue(String::class.java)
                        usernameText.text = Editable.Factory.getInstance().newEditable(username)
                        Log.d("Profile Username", "Ha encontrado un username, este es ${username}")
                    }
                }
            }
            .addOnFailureListener{ exception ->
                Log.d("Profile Username", "Error: ${exception.message}")
            }
    }

    private  fun  updateUsername(userId: String?){

        if(userId == null){
            Toast.makeText(this, "Invalid user", Toast.LENGTH_SHORT).show()
            return
        }

        val newUsername = usernameText.text.toString()
        val updatedData = mapOf(
            "user" to newUsername
        )
        database.child(userId).updateChildren(updatedData)
            .addOnSuccessListener {
                Log.d("Database", "Información del usuario actualizada correctamente")
                Toast.makeText(this, "Username updated", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Log.e("Database", "Error al actualizar información del usuario", exception)
            }
    }
}