package com.example.pathhunt.pathhuntkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart.setOnClickListener {
            val intent = Intent(this, IntroductionActivity::class.java)
            //for testing purposes, gebeurt normaal in teamNameEditor
            //teamScore=0
            //
            startActivity(intent)
        }

        btnLeaderboard.setOnClickListener {
            val intent = Intent(this,Leaderboard::class.java)
            startActivity(intent)
        }
    }
}
