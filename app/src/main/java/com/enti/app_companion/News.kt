package com.enti.app_companion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import models.NewsAdapter
import models.NewsModel

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
        )
        val newsRight = listOf(
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