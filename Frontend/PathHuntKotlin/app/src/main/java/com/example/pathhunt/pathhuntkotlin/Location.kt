package com.example.pathhunt.pathhuntkotlin

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class Location(
    val id: Int,
    val name: String,
    val street: String
)
{
    class Deserializer: ResponseDeserializable<Array<Location>>{
        override fun deserialize(content: String): Array<Location>? = Gson().fromJson(content, Array<Location>::class.java)
        }
    }
