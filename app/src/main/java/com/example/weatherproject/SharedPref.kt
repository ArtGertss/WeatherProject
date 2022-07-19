package com.example.weatherproject

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson

class SharedPref private constructor() {

    companion object {
        @JvmStatic
        val instance: SharedPref by lazy { SharedPref() }

        private const val USER_FAV_KEY = "user_prefs_favorites"
    }

    private val gson = Gson()

    private val userSharedPrefs : SharedPreferences? = MyWeatherApp.context
        ?.getSharedPreferences("current_user_prefs.v1", Context.MODE_PRIVATE)

    var favoriteLocations: FavoritesModel?
        get() = gson.fromJson(userSharedPrefs?.getString(USER_FAV_KEY, ""), FavoritesModel::class.java)
        set(value) {
            userSharedPrefs?.edit { putString(USER_FAV_KEY, gson.toJson(value)) }
        }
}