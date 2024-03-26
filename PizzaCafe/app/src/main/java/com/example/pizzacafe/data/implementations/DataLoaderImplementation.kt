package com.example.pizzacafe.data.implementations

import com.example.pizzacafe.domain.entities.Pizza
import com.example.pizzacafe.domain.interfaces.DataLoaderInterface
import javax.inject.Inject

class DataLoaderImplementation @Inject constructor(): DataLoaderInterface {
    override suspend fun getPizzaList(): List<Pizza> {
        TODO("Not yet implemented")
    }
}