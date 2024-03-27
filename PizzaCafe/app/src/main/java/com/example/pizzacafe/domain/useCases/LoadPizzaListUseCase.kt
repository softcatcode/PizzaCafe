package com.example.pizzacafe.domain.useCases

import com.example.pizzacafe.domain.entities.MenuItem
import com.example.pizzacafe.domain.interfaces.DataLoaderInterface
import com.example.pizzacafe.presentation.ui.menu.MenuSection
import javax.inject.Inject

class LoadPizzaListUseCase @Inject constructor(
    private val repository: DataLoaderInterface
) {
    suspend operator fun invoke(category: MenuSection): List<MenuItem> {
        return repository.getPizzaList(category)
    }
}