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

        setupHeader();

        val records = listOf(
            RecordModel(R.drawable.jokers, 1000, 10, 10),
            RecordModel(R.drawable.jokers, 1000, 10, 10),
            RecordModel(R.drawable.jokers, 1000, 10, 10),
            RecordModel(R.drawable.jokers, 1000, 10, 10),
            RecordModel(R.drawable.jokers, 1000, 10, 10),
            RecordModel(R.drawable.jokers, 1000, 10, 10),
            RecordModel(R.drawable.jokers, 1000, 10, 10),
            RecordModel(R.drawable.jokers, 1000, 10, 10),
            RecordModel(R.drawable.jokers, 1000, 10, 10),

            )

        val recyclerView : RecyclerView = findViewById(R.id.record_recycle_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
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