package com.example.pathhunt.pathhuntkotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_introduction.*


class IntroductionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)
        txtIntroduction.text = "Follow the path to the designated area. Once you're near the monument a question will pop-up concerning said monument. You'll get 60 seconds to answer the question. " +
                "In total you can earn 60 points. Every 5 seconds, 5 points wil be withdrawn from the total amount you can earn. If given the wrong answer, you get zero points and there'll be other " +
                "consequences for a wrong answser. Good luck and happy hunting!"

        btnNext.setOnClickListener {
            val intent = Intent(this,QuestionActivity::class.java)
            startActivity(intent)
        }
}}