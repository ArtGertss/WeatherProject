package com.example.weatherproject.ui.favorites

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.weatherproject.FavoritesModel
import com.example.weatherproject.databinding.ItemFavoritesBinding

class FavoritesAdapter(var favoritesList: FavoritesModel, val listener: FavoritesItemListener) :
    RecyclerView.Adapter<FavoritesAdapter.FavoritesAdapterViewHolder>() {

    inner class FavoritesAdapterViewHolder(private val binding: ItemFavoritesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            with(binding) {
                locationName.text = favoritesList.locationsList[position].name
                latitude.text = "%.2f".format(favoritesList.locationsList[position].latitude)
                longitude.text = "%.2f".format(favoritesList.locationsList[position].longitude)
                btnDeleteItem.setOnClickListener {
                    listener.deleteItem(position)
                }
                root.setOnClickListener {
                    listener.itemClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesAdapterViewHolder {
        val binding =
            ItemFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesAdapterViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return favoritesList.locationsList.size
    }

    interface FavoritesItemListener {
        fun itemClick(id: Int)
        fun deleteItem(id: Int)
    }
}