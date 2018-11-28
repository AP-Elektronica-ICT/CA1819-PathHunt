package com.example.pathhunt.pathhuntkotlin

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class Team (
    val id: Int?,
    val name: String,
    val score: Int
){
    class Deserializer: ResponseDeserializable<Array<Team>>{
        override fun deserialize(content: String): Array<Team>? {
            return Gson().fromJson(content, Array<Team>::class.java)
        }
    }
}

