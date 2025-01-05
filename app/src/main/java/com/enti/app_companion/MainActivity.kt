package com.enti.app_companion

import HelldiversIIApi.HelldiversIIApiInstance
import HelldiversIIApi.HelldiversIIResponse
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        setupHeader();

        val call = HelldiversIIApiInstance.apiService.getPlanets()

        call.enqueue(object : Callback<HelldiversIIResponse> {
            override fun onResponse(call: Call<HelldiversIIResponse>, response: Response<HelldiversIIResponse>) {
                if (response.isSuccessful) {
                    val planets  = response.body()?.data?.results
                    planets?.forEach { planet ->
                        Log.d("Planet", "Name : ${planet.index}, Description: ${planet.name}")
                    }
                }else {
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