package com.enti.app_companion

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import models.NewsAdapter
import models.NewsModel

class News : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_news)

        setupHeader()

        // Lista de noticias para el lado izquierdo del RecyclerView
        val newsLeft = listOf(
            NewsModel("Among Us", R.drawable.among_us_cards),
            NewsModel("Binding of Isaac", R.drawable.issaac_cards),
            NewsModel("Play In Physical", R.drawable.group_of_cards),
            NewsModel("Slay The Spire", R.drawable.slay_the_spire_cards),
            NewsModel("Among Us", R.drawable.among_us_cards),
            NewsModel("Binding of Isaac", R.drawable.issaac_cards),
            NewsModel("Play In Physical", R.drawable.group_of_cards),
            NewsModel("Slay The Spire", R.drawable.slay_the_spire_cards),
        )

        // Lista de noticias para el lado derecho del RecyclerView
        val newsRight = listOf(
            NewsModel("Play On Mobile", R.drawable.google_play),
            NewsModel("New Patch", R.drawable.group_of_cards),
            NewsModel("The Witcher 3", R.drawable.the_witcher_iii_cards),
            NewsModel("Cyberpunk 2077", R.drawable.cyberpunk_cards),
            NewsModel("Play On Mobile", R.drawable.google_play),
            NewsModel("New Patch", R.drawable.group_of_cards),
            NewsModel("The Witcher 3", R.drawable.the_witcher_iii_cards),
            NewsModel("Cyberpunk 2077", R.drawable.cyberpunk_cards),
        )

        // Configuración de los RecyclerViews izquierdo y derecho
        val recyclerLeftView: RecyclerView = findViewById(R.id.news_left_recycler_view)
        val recyclerRightView: RecyclerView = findViewById(R.id.news_right_recycler_view)

        recyclerLeftView.layoutManager = LinearLayoutManager(this)
        recyclerRightView.layoutManager = LinearLayoutManager(this)

        recyclerLeftView.adapter = NewsAdapter(newsLeft) // Adaptador para el lado izquierdo
        recyclerRightView.adapter = NewsAdapter(newsRight) // Adaptador para el lado derecho
    }

    private fun setupHeader() {
        // Configura los botones del encabezado y define cuál está activo
        HeaderUtils.setupHeader(
            this,
            mapOf(
                findViewById<Button>(R.id.header_button_1) to News::class.java,
                findViewById<Button>(R.id.header_button_2) to Chat::class.java,
                findViewById<Button>(R.id.header_button_3) to Profile::class.java,
                findViewById<Button>(R.id.header_button_4) to Jokers::class.java,
                findViewById<Button>(R.id.header_button_5) to Record::class.java
            ),
            findViewById(R.id.header_button_1) // Marca el botón News como activo
        )
    }
}
