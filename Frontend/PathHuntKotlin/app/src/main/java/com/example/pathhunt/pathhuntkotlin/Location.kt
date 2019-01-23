package com.example.pathhunt.pathhuntkotlin

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class Locatie(
    val id: Int,
    val name: String,
    val street: String
)
{
    class Deserializer: ResponseDeserializable<Array<Locatie>>{
        override fun deserialize(content: String): Array<Locatie>?{
            return Gson().fromJson(content, Array<Locatie>::class.java)}
        }
    }

    class SingleDeserializer: ResponseDeserializable<Location>{
        override fun deserialize(content: String): Location? = Gson().fromJson(content, Location::class.java)
    }
}




