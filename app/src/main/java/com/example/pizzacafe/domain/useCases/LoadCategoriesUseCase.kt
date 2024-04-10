package com.example.pizzacafe.domain.useCases

import com.example.pizzacafe.domain.entities.Category
import com.example.pizzacafe.domain.interfaces.DataLoaderInterface
import javax.inject.Inject

class LoadCategoriesUseCase @Inject constructor(
    private val repository: DataLoaderInterface
) {
    suspend operator fun invoke(): List<Category> {
        return repository.getCategoryList()
    }
}