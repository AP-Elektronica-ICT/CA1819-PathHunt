package com.example.pathhunt.pathhuntkotlin

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import com.github.kittinunf.fuel.httpGet

class Leaderboard : AppCompatActivity() {

    val teamList = arrayListOf<Team>(/*
        Team(null, "Team1", 100),
        Team(null, "Team2", 100),
        Team(null, "Team3", 100)*/)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        val listView = findViewById<ListView>(R.id.score_list_view)

        GetTeam(URL, listView)

    }

    private class ListAdapter(context: Context, teamList: ArrayList<Team>): BaseAdapter(){
        private val context: Context
        private val teamList: ArrayList<Team>

        init {
            this.context = context
            this.teamList = teamList
            this.teamList.sortWith(compareBy(Team::score, Team::name))
            this.teamList.reverse()
        }

        override fun getCount(): Int {
            return teamList.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return "Test String"
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(context)
            val rowList = layoutInflater.inflate(R.layout.row_list, parent, false)

            val nameList = rowList.findViewById<TextView>(R.id.txtName)
            nameList.text = teamList.get(position).name

            val scoreList = rowList.findViewById<TextView>(R.id.txtScore)
            scoreList.text = teamList.get(position).score.toString()

            return rowList

            /*val textView = TextView(context)
            textView.text = "Tadahhhh"
            return textView*/
        }
    }

    fun GetTeam(URL: String, listView: ListView) {
        URL.httpGet().responseObject(Team.Deserializer()) { request, response, result ->
            val (teams, err) = result
            teams?.forEach { team ->
                teamList.add(team)
            }
            //teamList.sortWith(compareBy(Team::score))
            listView.adapter = ListAdapter(this, teamList)
        }
    }


}
