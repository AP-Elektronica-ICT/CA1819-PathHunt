package com.example.pathhunt.pathhuntkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCreateTeam.setOnClickListener {
            val intent = Intent(this, TeamNameEditor::class.java)
            startActivity(intent)
        }
        btnContinue.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
        }
        btnIntroduction.setOnClickListener {
            val intent = Intent(this,IntroductionActivity::class.java)
            startActivity(intent)
        }
        btnMaps.setOnClickListener {
            val intent = Intent(this,MapsActivity::class.java)
            startActivity(intent)
        }
    }
}