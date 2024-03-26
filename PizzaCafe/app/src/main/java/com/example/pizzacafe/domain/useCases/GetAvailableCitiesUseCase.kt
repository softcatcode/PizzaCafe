package com.example.pizzacafe.domain.useCases

import com.example.pizzacafe.domain.interfaces.DataLoaderInterface
import javax.inject.Inject

class GetAvailableCitiesUseCase @Inject constructor(
    private val loader: DataLoaderInterface
) {
    operator fun invoke(): List<String> {
        return loader.getAvailableCities()
    }
}