package com.example.weatherproject


data class WeatherModel(
    var weather: List<Weather>,
    var main: Main,
    var visibility: Int,
    var wind: Wind,
    var name: String,
    var clouds: Clouds

)

data class Weather (
    var id: Int,
    var main: String,
    var description: String,
    var icon: String
)

data class Main(
    var temp: Float,
    var feels_like: Float,
    var temp_min: Float,
    var temp_max: Float,
    var pressure: Int,
    var humidity: Int
)

data class Wind (
    var speed: Double,
    var deg: Int
        )

data class Clouds(
    var all: Int
)
