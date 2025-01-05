package com.enti.app_companion

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import models.JokersAdapter
import models.JokersModel

class Jokers : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_jokers)

        setupHeader()

        val jokers = listOf(
            JokersModel("Among Us", R.drawable.jokers),
            JokersModel("Binding of Isaac", R.drawable.jokers),
            JokersModel("Play In Physical", R.drawable.jokers),
            JokersModel("Slay The Spire", R.drawable.jokers),
            JokersModel("Among Us", R.drawable.jokers),
            JokersModel("Binding of Isaac", R.drawable.jokers),
            JokersModel("Play In Physical", R.drawable.jokers),
            JokersModel("Slay The Spire", R.drawable.jokers),
            JokersModel("Among Us", R.drawable.jokers),
            JokersModel("Binding of Isaac", R.drawable.jokers),
            JokersModel("Play In Physical", R.drawable.jokers),
            JokersModel("Slay The Spire", R.drawable.jokers),
            JokersModel("Among Us", R.drawable.jokers),
            JokersModel("Binding of Isaac", R.drawable.jokers),
            JokersModel("Play In Physical", R.drawable.jokers),
            JokersModel("Slay The Spire", R.drawable.jokers),
        )

        val recyclerView : RecyclerView = findViewById(R.id.jokers_recycle_view)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = JokersAdapter(jokers)
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
            findViewById(R.id.header_button_4)
        )
    }
}