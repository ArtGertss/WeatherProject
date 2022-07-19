package com.example.weatherproject.ui.home

import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.weatherproject.*
import com.example.weatherproject.data.ApiInterface
import com.example.weatherproject.data.ServiceBuilder
import com.example.weatherproject.models.AutocompleteModel
import com.example.weatherproject.models.Coordinates
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
class HomeViewModel(appContext: Application) : AndroidViewModel(appContext) {
    private val uiScope = CoroutineScope(Dispatchers.IO)
    val weatherData = MutableLiveData<WeatherModel>()
    val forecastData = MutableLiveData<ForecastModel>()
    var weatherIcon = MutableLiveData<Int>()
    var coordinates = MutableLiveData<Coordinates>()
    var locationData = MutableLiveData<List<AutocompleteModel>>()
    var city = MutableLiveData<String>()
    var state = MutableLiveData<String>()
    var country = MutableLiveData<String>()
    private val service: ApiInterface =
        ServiceBuilder.getInstance().create(ApiInterface::class.java)
    private val apiKey = "e6e099ec023db5943241f3661ea01d1d"

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(appContext)

    fun requestWeatherAndForecastData() {
        uiScope.launch {
            service.requestWeather(
                coordinates.value?.latitude.toString(),
                coordinates.value?.longitude.toString(),
                "metric",
                apiKey
            ).body()?.let {
                weatherData.postValue(it)
                determineWeatherIcon(it.weather[0].icon)
            }

            service.requestForecast(
                coordinates.value?.latitude.toString(),
                coordinates.value?.longitude.toString(),
                "metric",
                "24",
                apiKey
            ).body()?.let { forecastData.postValue(it) }
        }
    }

    fun requestLocationData(query: String) {
        uiScope.launch {
            val result = service.requestLocation(query, "5", apiKey)
            locationData.postValue(result.body())
        }
    }

    private fun requestReverseLocation(latitude: Double, longitude: Double) {
        uiScope.launch {
            val result = service.requestReverseLocation(
                latitude.toString(),
                longitude.toString(),
                "5",
                apiKey
            )
            val cityInfo: List<AutocompleteModel> = result.body()!!
            city.postValue(cityInfo[0].city_name)
            state.postValue(cityInfo[0].state_name)
            country.postValue(cityInfo[0].country_name)
        }
    }

    private fun determineWeatherIcon(iconID: String) {
        weatherIcon.postValue(
            when (iconID) {
                "01d" -> R.drawable.ic_01d
                "02d" -> R.drawable.ic_02d
                "03d" -> R.drawable.ic_03d
                "04d" -> R.drawable.ic_04d
                "09d" -> R.drawable.ic_09d
                "10d" -> R.drawable.ic_10d
                "11d" -> R.drawable.ic_11d
                "13d" -> R.drawable.ic_13d
                "50d" -> R.drawable.ic_50d
                "01n" -> R.drawable.ic_01n
                "02n" -> R.drawable.ic_02n
                "03n" -> R.drawable.ic_03n
                "04n" -> R.drawable.ic_04n
                "09n" -> R.drawable.ic_09n
                "10n" -> R.drawable.ic_10n
                "11n" -> R.drawable.ic_11n
                "13n" -> R.drawable.ic_13n
                "50n" -> R.drawable.ic_50n
                else -> R.drawable.ic_01d
            }
        )
    }

    fun requestLastLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    Log.e("test", "lat: ${location.latitude}, lon: ${location.longitude}")
                    coordinates.value = Coordinates(location.latitude, location.longitude)
                    requestWeatherAndForecastData()
                    requestReverseLocation(location.latitude, location.longitude)
                }
            }
    }

    fun saveCurrentLocationToFavorites() {
        val location = FavoriteLocation(
            city.value!!,
            coordinates.value!!.latitude,
            coordinates.value!!.longitude
        )
        val myModel = SharedPref.instance.favoriteLocations ?: FavoritesModel()
        myModel.locationsList.add(location)

        SharedPref.instance.favoriteLocations = myModel
    }

    fun requestFromFavoritesId(id: Int) {
        val favoritesModel = SharedPref.instance.favoriteLocations
        val location = favoritesModel?.locationsList?.get(id)
        if (location != null) {
            coordinates.value = Coordinates(location.latitude, location.longitude)

            val query = location.name
            requestLocationData(query)
            requestWeatherAndForecastData()
            requestReverseLocation(location.latitude, location.longitude)
        }

    }
}
