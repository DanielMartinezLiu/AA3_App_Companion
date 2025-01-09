package com.enti.app_companion

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import MarvelApi.MarvelApiInstance
import models.MarvelResponse
import models.NewsAdapter
import models.NewsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigInteger
import java.security.MessageDigest

class News : AppCompatActivity() {
    private val publicKey = "4a47441d0ad2b1d769d917af032b6810"
    private val privateKey = "ef96aac3a810b9b9f4890c417b8e23301a1c893f"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_news)

        setupHeader();
        val newsLeft = listOf(
            NewsModel("Among Us", R.drawable.jokers),
            NewsModel("Binding of Isaac", R.drawable.jokers),
            NewsModel("Play In Physical", R.drawable.jokers),
            NewsModel("Slay The Spire", R.drawable.jokers),
            NewsModel("Among Us", R.drawable.jokers),
            NewsModel("Binding of Isaac", R.drawable.jokers),
            NewsModel("Play In Physical", R.drawable.jokers),
            NewsModel("Slay The Spire", R.drawable.jokers),
        )
        val newsRight = listOf(
            NewsModel("Play On Mobile", R.drawable.jokers),
            NewsModel("New Patch", R.drawable.jokers),
            NewsModel("The Witcher 3", R.drawable.jokers),
            NewsModel("Cyberpunk 2077", R.drawable.jokers),
            NewsModel("Play On Mobile", R.drawable.jokers),
            NewsModel("New Patch", R.drawable.jokers),
            NewsModel("The Witcher 3", R.drawable.jokers),
            NewsModel("Cyberpunk 2077", R.drawable.jokers),
        )

        val recyclerLeftView : RecyclerView = findViewById(R.id.news_left_recycler_view)
        val recyclerRightView : RecyclerView = findViewById(R.id.news_right_recycler_view)

        recyclerLeftView.layoutManager = LinearLayoutManager(this)
        recyclerRightView.layoutManager = LinearLayoutManager(this)

        recyclerLeftView.adapter = NewsAdapter(newsLeft)
        recyclerRightView.adapter = NewsAdapter(newsRight)

        val timestamp = System.currentTimeMillis().toString()
        val hash = md5("$timestamp$privateKey$publicKey")
        val call = MarvelApiInstance.apiService.getCharacters(publicKey, timestamp, hash)

        call.enqueue(object : Callback<MarvelResponse> {
            override fun onResponse(call: Call<MarvelResponse>, response: Response<MarvelResponse>) {
                if(response.isSuccessful) {
                    val characters = response.body()?.data?.results
                    characters?.forEach { character ->
                        Log.d("Character", "Name: ${character.name}, Description: ${character.description}")
                    }
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

    private fun setupHeader()
    {
        HeaderUtils.setupHeader(
            this,
            mapOf(
                findViewById<Button>(R.id.header_button_1) to News::class.java,
                findViewById<Button>(R.id.header_button_2) to Chat::class.java,
                findViewById<Button>(R.id.header_button_3) to Profile::class.java,
                findViewById<Button>(R.id.header_button_4) to Jokers::class.java,
                findViewById<Button>(R.id.header_button_5) to Record::class.java
            ),
            findViewById(R.id.header_button_1)
        )
    }
}