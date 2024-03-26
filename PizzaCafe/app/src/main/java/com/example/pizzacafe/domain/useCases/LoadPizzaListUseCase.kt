package com.example.pizzacafe.domain.useCases

import com.example.pizzacafe.domain.entities.Pizza
import com.example.pizzacafe.domain.interfaces.DataLoaderInterface
import javax.inject.Inject

class LoadPizzaListUseCase @Inject constructor(
    private val repository: DataLoaderInterface
) {
    suspend operator fun invoke(): List<Pizza> {
        return repository.getPizzaList()
    }
}