package com.example.pizzacafe.domain.interfaces

import com.example.pizzacafe.domain.entities.Pizza

interface DataLoaderInterface {
    suspend fun getPizzaList(): List<Pizza>
}