package com.example.pizzacafe.presentation.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.pizzacafe.R
import com.example.pizzacafe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupNavigationBar()
        setupCitySelector()
    }

    private fun setupCitySelector() {
        val cityList = mutableListOf("Moscow", "Peter", "Omsk", "Volgograd", "Other")
        binding.citySelectorText.setText(cityList.first())
        val citiesAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cityList)
        binding.citySelectorText.setAdapter(citiesAdapter)
    }

    private fun setupNavigationBar() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
    }
}