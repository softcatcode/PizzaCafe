package com.example.pizzacafe.data.implementations

import com.example.pizzacafe.domain.entities.MenuItem
import com.example.pizzacafe.domain.interfaces.DataLoaderInterface
import com.example.pizzacafe.presentation.ui.menu.MenuSection
import javax.inject.Inject

class DataLoaderImplementation @Inject constructor(): DataLoaderInterface {
    override suspend fun getPizzaList(city: String, category: MenuSection): List<MenuItem> {
        return mutableListOf()
    }

    override fun getAvailableCities(): List<String> {
        return mutableListOf("Moscow", "Paris", "Amsterdam")
    }
}