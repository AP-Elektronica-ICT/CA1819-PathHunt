package com.example.pathhunt.pathhuntkotlin

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.github.kittinunf.fuel.gson.responseObject
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
        }
        catch (e: Exception) {
            Log.d("ErrorHttpPost", e.message)
        }
    }

    private fun GetTeam(){
        doAsync {
            val URL: String = "http://172.16.249.252:45455/api/teams"

            val bodyJson = """
                { "name" : "foo",
                "score" : "bar"
                }
                """

            URL.httpPost().responseObject { request, response, result ->

            }
            uiThread {
                Toast.makeText(this@TeamNameEditor,response.toString() , Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun MakeToast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}
