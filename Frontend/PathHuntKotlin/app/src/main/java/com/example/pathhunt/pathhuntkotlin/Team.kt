package com.example.pathhunt.pathhuntkotlin

interface Team {
    val teamname: String
    val score: Int
    //val email: String
}

data class getTeam(val id: Int,
                   override val teamname: String,
                   override val score: Int): Team

data class  postTeam(override val teamname: String,
                     override val score: Int = 0):Team
