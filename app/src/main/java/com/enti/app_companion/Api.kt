package com.enti.app_companion

import MarvelApi.MarvelApiInstance
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import models.MarvelCharacter
import models.MarvelResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigInteger
import java.security.MessageDigest

class Api : AppCompatActivity() {

    private val publicKey = "4a47441d0ad2b1d769d917af032b6810"
    private val privateKey = "ef96aac3a810b9b9f4890c417b8e23301a1c893f"
    private val charactersList = mutableListOf<MarvelCharacter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_api)

        callApi()

    }

    private fun createLayout() {

    }

    private fun callApi() {
        val timestamp = System.currentTimeMillis().toString()
        val hash = md5("$timestamp$privateKey$publicKey")
        val call = MarvelApiInstance.apiService.getCharacters(publicKey, timestamp, hash)

        call.enqueue(object : Callback<MarvelResponse> {
            override fun onResponse(call: Call<MarvelResponse>, response: Response<MarvelResponse>) {
                if(response.isSuccessful) {
                    val characters = response.body()?.data?.results
                    characters?.forEach { character ->
                        charactersList.add(character)
                    }
                    createLayout()

                }else {
                    Log.e("ApiError", "Response not successful: ${response.code()} - ${response.message()}")
                }
            }
            override fun onFailure(call: Call<MarvelResponse>, t: Throwable) {
                Log.e("ApiError", t.message ?: "Unknown Error")
            }
        })


    }

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}