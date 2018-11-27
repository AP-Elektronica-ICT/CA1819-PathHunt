package com.example.pathhunt.pathhuntkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_team_name_editor.*

val URL: String = "http://192.168.1.37:45455/api/teams"

class TeamNameEditor : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_name_editor)

        btnContinue.setOnClickListener {
            val team = Team(null, etxtTeamname.text.toString(), 0)

            CreateTeam(URL, team)
            finish()
        }
    }
}
