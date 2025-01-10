package com.enti.app_companion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class Register : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val mailText : EditText = findViewById(R.id.mail_field)
        val userText : EditText = findViewById(R.id.username_field)
        val passwordText : EditText = findViewById(R.id.password_field)
        val repeatedPasswordText : EditText = findViewById(R.id.repeat_password_field)

        val databaseUrl = "https://appcompanion-5f7f6-default-rtdb.europe-west1.firebasedatabase.app"
        database = FirebaseDatabase.getInstance(databaseUrl).getReference("users")
        val dataId = database.push().key

        val loginButton: Button = findViewById(R.id.login_button)
        loginButton.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        val registerButton: Button = findViewById(R.id.register_button)
        registerButton.setOnClickListener {
            val mailContent = mailText.text.toString()
            val userContent = userText.text.toString()
            val passwordContent = passwordText.text.toString()
            val repeatedPasswordContent = repeatedPasswordText.text.toString()

            //Si las contraseñas coinciden mostrar error
            if(passwordContent != repeatedPasswordContent){
                //Mostrar texto no funca
                Log.d("Register", "La contraseña no es correcta");
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT)
                return@setOnClickListener
            }

            Log.d("Register", "La contraseña es correcta");

            //Si el mail ya existe mostrar error
            val query: Query = database.orderByChild("mail").equalTo(mailContent)

            query.get()
                .addOnSuccessListener { snapshot ->
                    Log.d("Register", "Query enviada")
                    if(!snapshot.exists()){
                        Log.d("Register", "El mail no esta en uso");
                        sendData(dataId, mailContent, userContent, passwordContent)
                    }
                    else{
                        Toast.makeText(this, "El mail esta en uso", Toast.LENGTH_SHORT)
                        Log.d("Register", "El mail esta en uso");
                    }


                }
                .addOnFailureListener { exception ->
                    Log.e("Register", "Error al consultar la base de datos", exception)
                }



        }
    }

    private fun sendData(dataId : String?, mailContent : String, userContent : String, passwordContent : String){
        //Enviar los datos de la base de datos
        val messageData = mapOf(
            "mail" to mailContent,
            "user" to userContent,
            "password" to passwordContent
        )

        if(dataId != null)
        {
            database.child(dataId).setValue(messageData)
                .addOnSuccessListener { result ->
                    Log.d("Register", "Registro correcto")
                    val intent = Intent(this, Login::class.java)
                    startActivity(intent)
                }
                .addOnFailureListener{ exception ->
                    Log.d("Register", "Error: ${exception.message}")
                }
        }
    }


}