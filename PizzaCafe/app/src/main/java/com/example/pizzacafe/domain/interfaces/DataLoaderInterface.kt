package com.example.pizzacafe.domain.interfaces

import com.example.pizzacafe.domain.entities.MenuItem
import com.example.pizzacafe.presentation.ui.menu.MenuSection

interface DataLoaderInterface {
    suspend fun getPizzaList(city: String, category: MenuSection): List<MenuItem>

    fun getAvailableCities(): List<String>
}