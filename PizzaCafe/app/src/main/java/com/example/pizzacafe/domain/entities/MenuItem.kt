package com.example.pizzacafe.domain.entities

data class MenuItem(
    val name: String = "",
    val price: UInt = 0U,
    val description: String = "",
    val id: Int = UNDEFINED_ID
) {
    companion object {
        private const val UNDEFINED_ID = -1
    }
}
