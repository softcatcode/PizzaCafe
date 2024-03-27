package com.example.pizzacafe.domain.interfaces

import com.example.pizzacafe.domain.entities.Banner
import com.example.pizzacafe.domain.entities.Category
import com.example.pizzacafe.domain.entities.MenuItem
import com.example.pizzacafe.presentation.ui.menu.MenuSection

interface DataLoaderInterface {
    suspend fun getMenuItemsList(category: String): List<MenuItem>

    suspend fun getCategoryList(): List<Category>

    suspend fun getBannerList(): List<Banner>
}