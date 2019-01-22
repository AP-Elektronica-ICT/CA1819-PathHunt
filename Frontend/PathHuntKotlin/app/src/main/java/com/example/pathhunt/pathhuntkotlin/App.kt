package com.example.pathhunt.pathhuntkotlin

import android.app.Application
//https://blog.teamtreehouse.com/making-sharedpreferences-easy-with-kotlin
val prefs: Prefs by lazy{
    App.prefs!!
}
class App : Application() {
    companion object {
        var prefs : Prefs? = null
    }

    override fun onCreate() {
        prefs = Prefs(applicationContext)
        super.onCreate()
    }
}