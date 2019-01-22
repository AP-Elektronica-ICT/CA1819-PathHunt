package com.example.pathhunt.pathhuntkotlin
import android.location.Location
import com.example.pathhunt.pathhuntkotlin.MapsActivity

class Api{
    val urlTeams: String = "http://pathhuntwebapi.azurewebsites.net/api/teams"
    val urlLocations: String = "http://pathhuntwebapi.azurewebsites.net/api/locations"
    val urlQuestions: String = "http://pathhuntwebapi.azurewebsites.net/api/questions"
    val urlGeocoding: String = "https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyB4HgIDhaV6sv3ddo_Xol9r4fDLj7RpOaU&address="
    val urlDirections: String = "https://maps.googleapis.com/maps/api/directions/json?destination=51.2289238,4.4026316&key=AIzaSyB4HgIDhaV6sv3ddo_Xol9r4fDLj7RpOaU&origin="
}