package com.example.pizzacafe.domain.interfaces

import com.example.pizzacafe.domain.entities.Pizza

interface DataLoaderInterface {
    fun getPizzaList(): List<Pizza>
}