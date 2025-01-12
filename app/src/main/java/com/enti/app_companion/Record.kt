package com.enti.app_companion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import models.JokersAdapter
import models.JokersModel
import models.RecordAdapter
import models.RecordModel

class Record : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_record)

        setupHeader()

        // Crea una lista de objetos RecordModel que representan los registros a mostrar
        val records = listOf(
            RecordModel(R.drawable.black_deck, 1000, 10, 10),
            RecordModel(R.drawable.zodiac_deck, 1000, 10, 10),
            RecordModel(R.drawable.erratic_deck, 1000, 10, 10),
            RecordModel(R.drawable.plasma_deck, 1000, 10, 10),
            RecordModel(R.drawable.nebula_deck, 1000, 10, 10),
            RecordModel(R.drawable.painted_deck, 1000, 10, 10),
            RecordModel(R.drawable.challenge_deck, 1000, 10, 10),
            RecordModel(R.drawable.anaglyph_deck, 1000, 10, 10),
            RecordModel(R.drawable.ghost_deck, 1000, 10, 10)
        )

        // Obtiene la referencia al RecyclerView desde el diseño
        val recyclerView: RecyclerView = findViewById(R.id.record_recycle_view)
        // Establece un LinearLayoutManager para organizar los elementos en una lista vertical
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Asigna un adaptador al RecyclerView para mostrar la lista de registros
        recyclerView.adapter = RecordAdapter(records)
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
            findViewById(R.id.header_button_5)
        )
    }
}