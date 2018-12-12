package com.example.pathhunt.pathhuntkotlin

class Api{
    val urlTeams: String = "http://192.168.137.1:45455/api/teams"
    val urlLocations: String = "http://172.16.161.195:45455/api/locations/1"
    val urlQuestions: String = "http://192.168.137.1:45455/api/questions"
    val urlGeocoding: String = "https://maps.googleapis.com/maps/api/geocode/json?address=\"Rijnkaai 100, 2000 Antwerpen, Belgium\"&key=AIzaSyAPwdADNSjGx-daM3Mx2HCpVNFfhlzf-lQ"
    val urlDirections: String = "https://maps.googleapis.com/maps/api/directions/json?origin=51.2297882,4.4149717&destination=51.2289238,4.4026316&key=AIzaSyAPwdADNSjGx-daM3Mx2HCpVNFfhlzf-lQ"
}