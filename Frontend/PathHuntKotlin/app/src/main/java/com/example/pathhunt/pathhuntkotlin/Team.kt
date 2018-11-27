package com.example.pathhunt.pathhuntkotlin

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.google.gson.Gson

data class Team (
    val id: Int?,
    val name: String,
    val score: Int
){
    class Deserializer: ResponseDeserializable<Array<Team>>{
        override fun deserialize(content: String): Array<Team>? {
            return  Gson().fromJson(content, Array<Team>::class.java)
        }
    }
}

public fun CreateTeam(URL: String ,team: Team) {
    URL.httpPost()
        .jsonBody(Gson().toJson(team))
        .response {_,_, result ->
            println("result: " + result.toString())
        }
}

public fun GetTeam(URL: String) {
    URL.httpGet().responseObject(Team.Deserializer()) { request, response, result ->
        val (teams, err) = result
        teams?.forEach { team ->
            println("Team: ${team.name}")
        }
    }
}