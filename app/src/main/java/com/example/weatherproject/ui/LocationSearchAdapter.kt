package com.example.weatherproject.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherproject.databinding.ItemRecyclerviewSearchBinding
import com.example.weatherproject.models.AutocompleteModel


class LocationSearchAdapter(val listener: ItemListener) :
    RecyclerView.Adapter<LocationSearchAdapter.LocationSearchViewHolder>() {
    var items: List<AutocompleteModel> = listOf()

    inner class LocationSearchViewHolder(private val binding: ItemRecyclerviewSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.recyclerCityName.text =
                "${items[position].city_name}, ${items[position].state_name}, ${items[position].country_name}"
            binding.root.setOnClickListener {
                listener.itemClick(items[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationSearchViewHolder {
        val binding =
            ItemRecyclerviewSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return LocationSearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationSearchViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface ItemListener {
        fun itemClick(item: AutocompleteModel)
    }
}