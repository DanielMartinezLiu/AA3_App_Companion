package com.enti.app_companion

import ChampionsApi.ChampionsApiInstance
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import models.CharacterAdapter
import models.CharacterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigInteger
import java.security.MessageDigest

class Api : AppCompatActivity() {

    private val publicKey = "RGAPI-2b5f40e6-c608-4e23-8edf-0a6142faadb0"

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
        val call = ChampionsApiInstance.apiService.getCharacters(publicKey)

        call.enqueue(object : Callback<CharacterResponse> {
            override fun onResponse(call: Call<CharacterResponse>, response: Response<CharacterResponse>) {
                if(response.isSuccessful) {
                    val characters = response.body()?.data?.results?.values?.toList()

                    if (characters != null) {
                        Log.d("ApiResponse", "Characters received: ${characters.size}")
                        for (character in characters) {
                            Log.d("ApiResponse", "Character - ID: ${character.id}, Name: ${character.name}, Tier: ${character.title}")
                        }
                    }
                    else {
                        Log.d("ApiResponse", "Response body: ${response.body()?.toString()}")
                        Log.e("ApiError", "Response not successful: ${response.code()} - ${response.message()}")
                    }
                    recyclerView.adapter = CharacterAdapter(characters ?: emptyList())
                }else {
                    Log.e("ApiError", "Response not successful: ${response.code()} - ${response.message()}")
                }
            }
            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                Log.e("ApiError", t.message ?: "Unknown Error")
            }
        })
    }
}