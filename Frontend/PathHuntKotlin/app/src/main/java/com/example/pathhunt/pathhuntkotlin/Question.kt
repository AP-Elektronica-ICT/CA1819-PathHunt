package com.example.pathhunt.pathhuntkotlin

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class Question(
    val answer: String,
    val content: String,
    val id: Int?
    //val location: Any?
) {
    class Deserializer : ResponseDeserializable<Array<Question>> {
        override fun deserialize(content: String): Array<Question>? {
            return Gson().fromJson(content, Array<Question>::class.java)
        }
    }
}