package com.example.weatherproject.models

import com.google.gson.annotations.SerializedName

data class AutocompleteModel(
    @SerializedName("name")
    var city_name: String,
    @SerializedName("country")
    var country_name: String,
    @SerializedName("state")
    var state_name: String,
    @SerializedName("lat")
    var latitude: Float,
    @SerializedName("lon")
    var longitude: Float
)