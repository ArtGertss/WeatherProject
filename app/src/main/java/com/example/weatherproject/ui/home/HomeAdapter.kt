package com.example.weatherproject.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.weatherproject.Forecast
import com.example.weatherproject.R
import com.example.weatherproject.databinding.ItemRecyclerHomeBinding
import java.text.SimpleDateFormat
import java.util.*

class HomeAdapter(var forecastData: List<Forecast>) :
    RecyclerView.Adapter<HomeAdapter.ForecastViewHolder>() {

    inner class ForecastViewHolder(private val binding: ItemRecyclerHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val data = forecastData[position]
            with(binding) {
                tvCardHeader.text = getDayOfWeekFromTimestamp(data.dt)
                tvCardHour.text = getHourFromTimestamp(data.dt)
                tvCardCondition.text = data.weather[0].main
                tvCardTemperature.text = String.format("%.0f°", data.main.temp)
                ivCardIcon.setImageResource(getWeatherIcon(data.weather[0].icon))
            }
        }

        private fun getDayOfWeekFromTimestamp(timestamp: Long): String {
            return SimpleDateFormat("EEE", Locale.ENGLISH).format(timestamp * 1000)
        }

        private fun getHourFromTimestamp(timestamp: Long): String {
            return SimpleDateFormat("Ha", Locale.ENGLISH).format(timestamp * 1000)
        }

        private fun getWeatherIcon(iconID: String): Int {
            return when (iconID) {
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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding =
            ItemRecyclerHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return forecastData.size
    }
}