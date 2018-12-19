package com.example.pathhunt.pathhuntkotlin

class Api{
    val urlTeams: String = "http://pathhuntwebapi.azurewebsites.net/api/teams"
    val urlLocations: String = "http://pathhuntwebapi.azurewebsites.net/api/locations"
    val urlQuestions: String = "http://pathhuntwebapi.azurewebsites.net/api/questions"
    val urlGeocoding: String = "https://maps.googleapis.com/maps/api/geocode/json?address=\"Rijnkaai 100, 2000 Antwerpen, Belgium\"&key=AIzaSyB4HgIDhaV6sv3ddo_Xol9r4fDLj7RpOaU"
    val urlDirections: String = "https://maps.googleapis.com/maps/api/directions/json?origin=51.2297882,4.4149717&destination=51.2289238,4.4026316&key=AIzaSyB4HgIDhaV6sv3ddo_Xol9r4fDLj7RpOaU"
}