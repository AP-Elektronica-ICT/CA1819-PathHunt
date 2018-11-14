package com.example.pathhunt.pathhuntkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_team_name_editor.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.longToast
import org.jetbrains.anko.uiThread
import java.net.URL
import khttp.post


class TeamNameEditor : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_name_editor)
        btnContinue.setOnClickListener {

            /*val intent = Intent(this, TeamEditor::class.java)
            intent.putExtra("Name", teamname)
            startActivity(intent)*/

            CreateTeam()
            finish()
        }
    }

    private fun CreateTeam() {
        val serverURL: String = "http://192.168.1.37:45455/api/teams"

        post(serverURL, data = Team(name = etxtTeamname.text.toString(), score = 0))
    }
}
