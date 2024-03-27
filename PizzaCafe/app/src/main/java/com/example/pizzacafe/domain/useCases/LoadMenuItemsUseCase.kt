package com.example.pizzacafe.domain.useCases

import com.example.pizzacafe.domain.entities.MenuItem
import com.example.pizzacafe.domain.interfaces.DataLoaderInterface
import com.example.pizzacafe.presentation.ui.menu.MenuSection
import javax.inject.Inject

class LoadMenuItemsUseCase @Inject constructor(
    private val repository: DataLoaderInterface
) {
    suspend operator fun invoke(category: String): List<MenuItem> {
        return repository.getMenuItemsList(category)
    }
}