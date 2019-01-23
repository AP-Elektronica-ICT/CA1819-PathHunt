package com.example.pathhunt.pathhuntkotlin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.kittinunf.fuel.httpPost
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_end.*

class EndActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)
        txtEndScore.text = "Your score is: ${prefs.teamScore.toString()}!"
        val team = Team(null, prefs.teamName, prefs.teamScore)
        Api().urlTeams.httpPost()
            .jsonBody(Gson().toJson(team))
            .response {_,_, result ->
                println("result: " + result.toString())
            }
        btnEnd.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


}
