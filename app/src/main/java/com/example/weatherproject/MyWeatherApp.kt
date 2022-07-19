package com.example.weatherproject

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

@SuppressLint("StaticFieldLeak")
class MyWeatherApp : Application() {

    companion object {
        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}