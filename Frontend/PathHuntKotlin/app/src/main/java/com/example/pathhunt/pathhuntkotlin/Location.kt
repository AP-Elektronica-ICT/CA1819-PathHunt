package com.example.pathhunt.pathhuntkotlin

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class Locatie(
    val id: Int,
    val name: String,
    val street: String,
    val extraStreet : String
)
{
    class Deserializer: ResponseDeserializable<Array<Locatie>>{
        override fun deserialize(content: String): Array<Locatie>?{
            return Gson().fromJson(content, Array<Locatie>::class.java)
        }
    }

    class SingleDeserializer: ResponseDeserializable<Locatie>{
        override fun deserialize(content: String): Locatie? = Gson().fromJson(content, Locatie::class.java)
    }
}

