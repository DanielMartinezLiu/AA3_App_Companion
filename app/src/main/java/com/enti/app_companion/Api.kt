package com.enti.app_companion

import GamesApi.GamesApiInstance
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import models.GamesAdapter
import models.DarkSoulsGamesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Api : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Configura diseño de borde a borde
        setContentView(R.layout.activity_api)

        setupHeader()

        recyclerView = findViewById(R.id.api_recycle_view)
        recyclerView.layoutManager = LinearLayoutManager(this) // Establece disposición de lista
        callApi()
    }

    private fun callApi() {
        val apiKey = "7edb2d0194fd4d6a8b214af51e3dca7d"

        // Llama al servicio de la API para obtener los datos de los personajes
        val call = GamesApiInstance.apiService.getCharacters(apiKey)

        // Encola la llamada a la API de forma asíncrona
        call.enqueue(object : Callback<DarkSoulsGamesResponse> {

            override fun onResponse(call: Call<DarkSoulsGamesResponse>, response: Response<DarkSoulsGamesResponse>) {
                if (response.isSuccessful) {
                    // Verifica si la respuesta es exitosa
                    val games = response.body()?.results

                    if (games != null) {
                        // Si la lista no está vacía, muestra en el log la cantidad de personajes
                        Log.d("ApiResponse", "Characters received: ${games.size}")
                        for (character in games) {
                            // Muestra los nombres de los personajes recibidos en el log
                            Log.d("ApiResponse", "Name: ${character.name}")
                        }
                        // Asigna los datos al adaptador del RecyclerView para mostrarlos en la interfaz
                        recyclerView.adapter = GamesAdapter(games)
                    } else {
                        // Si el cuerpo de la respuesta está vacío, registra un error
                        Log.d("ApiResponse", "Response body: ${response.body()?.toString()}")
                        Log.e("ApiError", "Response not successful: ${response.code()} - ${response.message()}")
                    }
                } else {
                    // Si la respuesta no es exitosa, registra el código y mensaje de error
                    Log.e("ApiError", "Response not successful: ${response.code()} - ${response.message()}")
                }
            }

            // Se ejecuta si ocurre un error durante la solicitud
            override fun onFailure(call: Call<DarkSoulsGamesResponse>, t: Throwable) {
                // Registra el mensaje de error
                Log.e("ApiError", t.message ?: "Unknown Error")
            }
        })
    }

    // Configura los botones del encabezado y sus destinos
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
            findViewById(R.id.header_button_3) // Indica el botón actualmente seleccionado
        )
    }
}
