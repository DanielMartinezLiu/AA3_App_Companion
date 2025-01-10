package com.enti.app_companion

import ChampionsApi.ChampionsApiInstance
import android.os.Bundle
import android.util.Log
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
        enableEdgeToEdge()
        setContentView(R.layout.activity_api)

        recyclerView = findViewById(R.id.api_recycle_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        callApi()
    }

    private fun callApi() {
        val apiKey = "7edb2d0194fd4d6a8b214af51e3dca7d"

        val call = ChampionsApiInstance.apiService.getCharacters(apiKey)

        call.enqueue(object : Callback<DarkSoulsGamesResponse> {
            override fun onResponse(call: Call<DarkSoulsGamesResponse>, response: Response<DarkSoulsGamesResponse>) {
                if(response.isSuccessful) {
                    val games = response.body()?.results

                    if (games != null) {
                        Log.d("ApiResponse", "Characters received: ${games.size}")
                        for (character in games) {
                            Log.d("ApiResponse", "Name: ${character.name}")
                        }
                    }
                    else {
                        Log.d("ApiResponse", "Response body: ${response.body()?.toString()}")
                        Log.e("ApiError", "Response not successful: ${response.code()} - ${response.message()}")
                    }
                    recyclerView.adapter = games?.let { GamesAdapter(it) }
                }else {
                    Log.e("ApiError", "Response not successful: ${response.code()} - ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DarkSoulsGamesResponse>, t: Throwable) {
                Log.e("ApiError", t.message ?: "Unknown Error")
            }
        })
    }
}