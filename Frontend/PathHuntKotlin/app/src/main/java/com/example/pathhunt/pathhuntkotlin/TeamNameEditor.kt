package com.example.pathhunt.pathhuntkotlin

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import kotlinx.android.synthetic.main.activity_team_name_editor.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class TeamNameEditor : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_name_editor)

        btnContinue.setOnClickListener {

            /*val intent = Intent(this, TeamEditor::class.java)
            intent.putExtra("Name", teamname)
            startActivity(intent)*/


            //val team = postTeam(teamname = etxtTeamname.text.toString())

            //val jsonString = Gson().toJson(team) as String

            //Toast.makeText(this, team.teamname, Toast.LENGTH_LONG).show()

            //Log.d("JSONTag", etxtTeamname.text.toString())

            //CreateTeam(team)

            GetTeam()

            finish()
        }
    }

    private fun CreateTeam(team: Team) {

        try {
            Log.d("TAG", "Post succesfull")
        } catch (e: Exception) {
            Log.d("ErrorHttpPost", e.message)
        }
    }

    private fun GetTeam() {

        val URL: String = "http://172.16.155.162:45455/api/teams"

        var names: String = ""

        URL.httpGet().responseObject(Team.Desserializer()) { request, response, result ->
            val (teams, err) = result
            teams?.forEach { team ->
                names += ", " + team.teamname
            }
            MakeToast(names)
        }

    }

    private fun MakeToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}
