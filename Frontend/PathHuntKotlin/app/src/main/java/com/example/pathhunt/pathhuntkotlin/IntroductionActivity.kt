package com.example.pathhunt.pathhuntkotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_introduction.*


class IntroductionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)

        btnNext.setOnClickListener {
            val intent = Intent(this,TeamNameEditor::class.java)
            startActivity(intent)
        }
}}