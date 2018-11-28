package com.example.pathhunt.pathhuntkotlin

import com.google.android.gms.maps.model.LatLng


data class Location(
    val name: String,
    val latLng : LatLng,
    val id: Int
)