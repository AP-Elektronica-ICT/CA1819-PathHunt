package com.example.pathhunt.pathhuntkotlin

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
//https://blog.teamtreehouse.com/making-sharedpreferences-easy-with-kotlin
class Prefs (context: Context){
    val title: String = "teamPreferences"
    val TEAM_NAME : String = "teamName"
    val TEAM_SCORE: String = "teamScore"
    val NEXT_LOCATION: String = "nextLocation"
    val NEXT_STREET : String = "nextStreet"
    val LOCATION_ID : String = "nextLocationId"
    val prefs: SharedPreferences = context.getSharedPreferences(title, MODE_PRIVATE)

    var teamName: String
    get() = prefs.getString(TEAM_NAME, "Default")
    set(value) = prefs.edit().putString(TEAM_NAME, value).apply()

    var teamScore: Int
    get() = prefs.getInt(TEAM_SCORE, 0)
    set(value) = prefs.edit().putInt(TEAM_SCORE, value).apply()

    var nextLocation: String?
    get() = prefs.getString(NEXT_LOCATION, "Van Oevelen")
    set(value) = prefs.edit().putString(NEXT_LOCATION, value).apply()

    var nextStreet: String?
    get() = prefs.getString(NEXT_STREET, "Rinkvenstraat 2, 2910 Essen, Belgium")
    set(value) = prefs.edit().putString(NEXT_STREET, value).apply()

    var nextLocationId: Int
    get() = prefs.getInt(LOCATION_ID, 1)
    set(value) = prefs.edit().putInt(LOCATION_ID, value).apply()

}