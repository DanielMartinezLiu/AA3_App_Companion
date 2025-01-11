package com.enti.app_companion

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query


class Profile : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private val databaseUrl = "https://appcompanion-5f7f6-default-rtdb.europe-west1.firebasedatabase.app"
    private lateinit var analytics: FirebaseAnalytics


    private lateinit var logoutButton: ImageButton
    private  lateinit var usernameText: EditText
    private  lateinit var confirmUsernameButton: Button
    private  var isGoogleUser = false
    private var lastUsername = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        database = FirebaseDatabase.getInstance(databaseUrl).getReference("users")
        database.addChildEventListener(createChildEventListener())
        analytics = FirebaseAnalytics.getInstance(this)
        auth = FirebaseAuth.getInstance()

        val userId : String? = auth.currentUser?.uid // Obtener el UID del usuario actual

        isGoogleUser = getIsGoogleUser()



        logoutButton = findViewById(R.id.logout_button)
        logoutButton.setOnClickListener{
            auth.signOut()

            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }

        usernameText = findViewById(R.id.username_text)

        if(isGoogleUser){
            loadGoogleUserData()
        }
        else{
            loadMailUserData(userId)
        }


        confirmUsernameButton = findViewById(R.id.confirm_username_button)

        confirmUsernameButton.setOnClickListener{
            if(isGoogleUser){
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
    private  fun getIsGoogleUser(): Boolean {

        val currentUser = FirebaseAuth.getInstance().currentUser ?: return false

        return currentUser.providerData.any { it.providerId == GoogleAuthProvider.PROVIDER_ID }
    }

    private  fun loadGoogleUserData(){
        val currentUser = FirebaseAuth.getInstance().currentUser ?: return
        //Nombre de usuario
        val displayName = currentUser.displayName
        usernameText.text = Editable.Factory.getInstance().newEditable(displayName)
        if (displayName != null)
            lastUsername = displayName

    }
    private  fun  loadMailUserData(userId: String?) {
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
                        if (username != null)
                            lastUsername = username

                    }
                }
            }
            .addOnFailureListener{ exception ->
                Log.d("Profile Username", "Error: ${exception.message}")
            }
    }

    private  fun  updateUsername(userId: String?) {

        if(userId == null){
            Toast.makeText(this, "Invalid user", Toast.LENGTH_SHORT).show()
            return
        }


        val newUsername = usernameText.text.toString()

        if(newUsername == ""){
            Toast.makeText(this, "Introduce un nombre valido", Toast.LENGTH_SHORT).show()
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.METHOD, "User ${lastUsername} tried to change the username but was not valid")
            analytics.logEvent("username_change", bundle)
            return
        }

        val updatedData = mapOf(
            "user" to newUsername
        )
        database.child(userId).updateChildren(updatedData)
            .addOnSuccessListener {
                Log.d("Database", "Información del usuario actualizada correctamente")
                Toast.makeText(this, "Username updated", Toast.LENGTH_SHORT).show()
                val bundle = Bundle()
                bundle.putString(FirebaseAnalytics.Param.METHOD, "Username changed from ${lastUsername} to ${newUsername}")
                analytics.logEvent("username_change", bundle)
                lastUsername = newUsername
            }
            .addOnFailureListener { exception ->
                Log.e("Database", "Error al actualizar información del usuario", exception)
                val bundle = Bundle()
                bundle.putString(FirebaseAnalytics.Param.METHOD, "Username change failed: $exception")
                analytics.logEvent("username_change", bundle)
            }
    }

    private  fun createChildEventListener(): ChildEventListener {

        return object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                if(isGoogleUser)
                    return

                val newUsername = snapshot.child("user").getValue(String::class.java)
                usernameText.text = Editable.Factory.getInstance().newEditable(newUsername)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
    }

}


