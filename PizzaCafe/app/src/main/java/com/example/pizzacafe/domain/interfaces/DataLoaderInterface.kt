package com.example.pizzacafe.domain.interfaces

import com.example.pizzacafe.domain.entities.MenuItem
import com.example.pizzacafe.presentation.ui.menu.MenuSection

interface DataLoaderInterface {
    suspend fun getPizzaList(category: MenuSection): List<MenuItem>
}