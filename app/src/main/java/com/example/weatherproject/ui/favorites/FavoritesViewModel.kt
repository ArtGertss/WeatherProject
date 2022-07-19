package com.example.weatherproject.ui.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherproject.FavoriteLocation
import com.example.weatherproject.FavoritesModel
import com.example.weatherproject.SharedPref

class FavoritesViewModel : ViewModel() {
    var favoritesList: MutableLiveData<MutableList<FavoriteLocation>> = MutableLiveData()

    init {
        favoritesList.value = SharedPref.instance.favoriteLocations?.locationsList
    }

    fun deleteFavoriteById(id: Int) {
        val newFav = favoritesList.value
        newFav?.let {
            it.removeAt(id)
            SharedPref.instance.favoriteLocations = FavoritesModel(it)
            favoritesList.value = it
        }
    }
}