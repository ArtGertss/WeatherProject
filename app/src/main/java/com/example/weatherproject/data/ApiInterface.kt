package com.example.weatherproject.data

import com.example.weatherproject.models.AutocompleteModel
import com.example.weatherproject.ForecastModel
import com.example.weatherproject.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("data/2.5/weather")
    suspend fun requestWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("units") units: String = "metric",
        @Query("appid") key: String
    ): Response<WeatherModel>

    @GET("data/2.5/forecast")
    suspend fun requestForecast(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("units") units: String = "metric",
        @Query("cnt") count: String,
        @Query("appid") key: String
    ): Response<ForecastModel>

    @GET("geo/1.0/direct")
    suspend fun requestLocation(
        @Query("q") query: String,
        @Query("limit") limit: String,
        @Query("appid") key: String
    ): Response<List<AutocompleteModel>>

    @GET("geo/1.0/reverse")
    suspend fun requestReverseLocation(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("limit") limit: String,
        @Query("appid") key: String
    ): Response<List<AutocompleteModel>>

}