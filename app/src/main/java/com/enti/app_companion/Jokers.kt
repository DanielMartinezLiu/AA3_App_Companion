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
            JokersModel("Joker", R.drawable.joker),
            JokersModel("Greedy Joker", R.drawable.greedy_joker),
            JokersModel("Lusty Joker", R.drawable.lusty_joker),
            JokersModel("Wrathful Joker", R.drawable.wrathful_joker),
            JokersModel("Gluttons Joker", R.drawable.gluttonous_joker),
            JokersModel("Jolly Joker", R.drawable.jolly_joker),
            JokersModel("Zany Joker", R.drawable.zany_joker),
            JokersModel("Mad Joker", R.drawable.mad_joker),
            JokersModel("Crazy Joker", R.drawable.crazy_joker),
            JokersModel("Droll Joker", R.drawable.droll_joker),
            JokersModel("Sly Joker", R.drawable.sly_joker),
            JokersModel("Wily Joker", R.drawable.wily_joker),
            JokersModel("Clever Joker", R.drawable.clever_joker),
            JokersModel("Devious Joker", R.drawable.devious_joker),
            JokersModel("Crafty Joker", R.drawable.crafty_joker),
            JokersModel("Half Joker", R.drawable.half_joker),
            JokersModel("Joker Stencil", R.drawable.joker_stencil),
            JokersModel("Four Fingers", R.drawable.four_fingers),
            JokersModel("Mime", R.drawable.mime),
            JokersModel("Credit Card", R.drawable.credit_card),
            JokersModel("Ceremonial Dagger", R.drawable.ceremonial_dagger),
            JokersModel("Banner", R.drawable.banner),
            JokersModel("Mystic Summit", R.drawable.mystic_summit),
            JokersModel("Marble Joker", R.drawable.marble_joker),
            JokersModel("Loyalty Card", R.drawable.loyalty_card),
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