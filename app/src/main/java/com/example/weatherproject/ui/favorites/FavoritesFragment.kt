package com.example.weatherproject.ui.favorites

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.weatherproject.FavoritesModel
import com.example.weatherproject.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myActivity = (activity as AppCompatActivity)
        myActivity.setSupportActionBar(binding.favoritesTopAppBar)
        myActivity.supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        binding.favoritesTopAppBar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        binding.recyclerFavorites.adapter = viewModel.favoritesList.value?.let {
            FavoritesAdapter(
                FavoritesModel(it),
                listener = object : FavoritesAdapter.FavoritesItemListener {
                    override fun itemClick(id: Int) {
                        val action =
                            FavoritesFragmentDirections.actionFavoritesFragmentToHomeFragment2()
                        action.id = id
                        Navigation.findNavController(view).navigate(action)
                    }

                    override fun deleteItem(id: Int) {
                        viewModel.deleteFavoriteById(id)
                    }
                })
        }

        viewModel.favoritesList.observe(viewLifecycleOwner) {
            viewModel.favoritesList.value?.let {
                with((binding.recyclerFavorites.adapter as FavoritesAdapter)) {
                    favoritesList = FavoritesModel(it)
                    notifyDataSetChanged()
                }
            }
        }
    }
}