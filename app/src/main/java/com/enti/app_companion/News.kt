package com.enti.app_companion

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import HelldiversIIApi.HelldiversIIApiInstance
import models.HelldiversIIResponse
import models.NewsAdapter
import models.NewsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class News : AppCompatActivity() {
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

        callApi()
    }

    private fun callApi()
    {
        HelldiversIIApiInstance.apiService.getPlanets().enqueue(object : Callback<HelldiversIIResponse> {
            override fun onResponse(call: Call<HelldiversIIResponse>, response: Response<HelldiversIIResponse>) {
                Log.d("ApiResponse", "Raw Response: ${response.raw()}")

                if (response.isSuccessful) {
                    val planets = response.body()?.data?.values

                    if (!planets.isNullOrEmpty()) {
                        planets.forEach { planet ->
                            Log.d("Planet", "Name: ${planet.name}, Sector: ${planet.sector}")
                        }
                    } else {
                        Log.e("ApiError", "No planets found in the response.")
                    }
                } else {
                    Log.e("ApiError", "Response not successful: ${response.code()} - ${response.message()}")
                }
            }
            override fun onFailure(call: Call<HelldiversIIResponse>, t: Throwable) {
                Log.e("ApiError", t.message ?: "Unknown error")
            }
        })
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