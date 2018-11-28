package com.example.pathhunt.pathhuntkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_team_name_editor.*


class TeamNameEditor : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_name_editor)
        btnContinue.setOnClickListener {
            val teamname = etxtTeamname.text.toString()

            CreateTeam(Api().url, team)
            finish()
        }
    }

    private fun CreateTeam(team: Team) {
        val serverURL: String = "http://192.168.1.37:45455/api/teams"
    }
}
