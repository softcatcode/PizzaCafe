package com.example.pizzacafe.domain.entities

data class Category(
    val name: String = "",
    val id: Int = UNDEFINED_ID,
    val enabled: Boolean = false
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}