package com.example.pathhunt.pathhuntkotlin

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import kotlinx.android.synthetic.main.activity_team_name_editor.*
import javax.xml.transform.Result


class TeamNameEditor : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_name_editor)

        btnContinue.setOnClickListener {

            /*val intent = Intent(this, TeamEditor::class.java)
            intent.putExtra("Name", teamname)
            startActivity(intent)*/




            //val jsonString = Gson().toJson(team) as String

            //Toast.makeText(this, team.teamname, Toast.LENGTH_LONG).show()

            //Log.d("JSONTag", etxtTeamname.text.toString())

            //CreateTeam(team)

            GetTeam()

            finish()
        }
    }

    private fun CreateTeam(team: Team) {

    }

    private fun GetTeam() {

        val URL: String = "http://192.168.1.37:45455/api/teams"

        var names: String = ""

        URL.httpGet().responseObject(Team.Deserializer()) { request, response, result ->
            val (teams, err) = result
            teams?.forEach { team ->
                println("Team: ${team.name}")
            }
            //MakeToast(names)
        }
        /*URL.httpGet().responseString(){request, response, result ->
            when(result){
                is Result.Success -> { println("Result: ${result.get()}")}
                is Result.Failure -> {}
            }
        }*/

    }

    private fun MakeToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}
