package com.example.weatherproject

data class ForecastModel (
        var list: List<Forecast>
        )

data class Forecast (
        var dt: Long,
        var dt_txt: String,
        var main: MainForecast,
        var weather: List<WeatherForecast>
        )

data class MainForecast (
        var temp: Float,
        )

data class WeatherForecast(
        var main: String,
        var icon: String
)