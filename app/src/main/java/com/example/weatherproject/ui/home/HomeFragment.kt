package com.example.weatherproject.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.weatherproject.models.AutocompleteModel
import com.example.weatherproject.ui.LocationSearchAdapter
import com.example.weatherproject.R

import com.example.weatherproject.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) viewModel.requestLastLocation()
        }
    private val args: HomeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_app_bar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myActivity = (activity as AppCompatActivity)
        myActivity.setSupportActionBar(binding.homeTopAppBar)

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            return
        }

        binding.homeTopAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search -> {
                    binding.searchLocationLayout.isVisible = !binding.searchLocationLayout.isVisible
                    true
                }
                R.id.gps_location -> {
                    viewModel.requestLastLocation()
                    viewModel.requestWeatherAndForecastData()
                    true
                }
                R.id.favorites -> {
                    Navigation.findNavController(view)
                        .navigate(R.id.action_homeFragment_to_favoritesFragment2)
                    true
                }
                R.id.save_favorites -> {
                    viewModel.saveCurrentLocationToFavorites()
                    Toast.makeText(context, R.string.saved_to_favorites, Toast.LENGTH_LONG).show()
                    true
                }
                else -> false
            }
        }

        binding.recyclerviewSearch.adapter =
            LocationSearchAdapter(listener = object : LocationSearchAdapter.ItemListener {
                override fun itemClick(item: AutocompleteModel) {
                    binding.recyclerviewSearch.isVisible = false
                    binding.searchLocationLayout.isVisible = false
                    with(viewModel) {
                        coordinates.value?.latitude = item.latitude.toDouble()
                        coordinates.value?.longitude = item.longitude.toDouble()
                        requestWeatherAndForecastData()
                        city.value = item.city_name
                        state.value = item.state_name
                        country.value = item.country_name
                    }
                }
            })

        Log.e("args", args.id.toString())
        if (args.id == -1) {
            viewModel.requestLastLocation()
            viewModel.requestWeatherAndForecastData()
        } else {
            viewModel.requestFromFavoritesId(args.id)
        }

        viewModel.forecastData.observe(viewLifecycleOwner) { forecastModel ->
            binding.recyclerviewForecast.adapter = HomeAdapter(forecastModel.list)
        }

        binding.searchLocation.setOnEditorActionListener { _, actionId, _ ->
            val query = binding.searchLocation.text.toString()
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.requestLocationData(query)
                binding.recyclerviewSearch.isVisible = true
                false
            } else {
                false
            }
        }

        viewModel.locationData.observe(viewLifecycleOwner) {
            (binding.recyclerviewSearch.adapter as LocationSearchAdapter).items = it
            (binding.recyclerviewSearch.adapter as LocationSearchAdapter).notifyDataSetChanged()
        }
    }
}