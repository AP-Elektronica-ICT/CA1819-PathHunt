package com.example.pathhunt.pathhuntkotlin

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

/*interface Team {
    val teamname: String
    val score: Int
    //val email: String
}

data class getTeam(val id: Int,
                   override val teamname: String,
                   override val score: Int): Team

data class  postTeam(override val teamname: String,
                     override val score: Int = 0):Team*/

data class Team (
    val teamname: String,
    val score: Int = 0
){
    class Desserializer: ResponseDeserializable<Array<Team>>{
        override fun deserialize(content: String): Array<Team>? {
            return  Gson().fromJson(content, Array<Team>::class.java)
        }
    }
}