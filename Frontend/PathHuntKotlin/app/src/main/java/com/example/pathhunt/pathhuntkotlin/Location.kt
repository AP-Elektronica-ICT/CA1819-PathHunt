package com.example.pathhunt.pathhuntkotlin

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson


data class Location(
    val name: String,
    val latLng : LatLng,
    val id: Int
){ class Deserializer: ResponseDeserializable<Array<Location>>{
    override fun deserialize(content: String): Array<Location>? {
        return Gson().fromJson(content, Array<Location>::class.java)
        }
    }
}