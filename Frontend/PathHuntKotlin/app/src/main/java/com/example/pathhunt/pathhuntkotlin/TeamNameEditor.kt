package com.example.pathhunt.pathhuntkotlin

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.kittinunf.fuel.httpPost
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_team_name_editor.*

class TeamNameEditor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_name_editor)

        btnContinue.setOnClickListener {
            val team = Team(null, etxtTeamname.text.toString(), 0)
            prefs.teamScore=0
            prefs.nextStreet="Ellermanstraat 33, 2060 Antwerpen"
            prefs.nextLocationId = 1
            prefs.nextLocation = "AP Hogeschool"
            prefs.numberOfQuestions = 0
            CreateTeam(Api().urlTeams, team)
            val intent = Intent(this, QuestionActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun CreateTeam(URL: String ,team: Team) {
        URL.httpPost()
            .jsonBody(Gson().toJson(team))
            .response {_,_, result ->
                println("result: " + result.toString())
            }
    }
}
