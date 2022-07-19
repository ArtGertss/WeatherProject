package com.example.weatherproject

data class FavoritesModel(
    var locationsList: MutableList<FavoriteLocation> = mutableListOf()
)

data class FavoriteLocation(
    var name: String,
    var latitude: Double,
    var longitude: Double
)