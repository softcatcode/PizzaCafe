package com.example.pizzacafe.presentation.ui.menu

import com.example.pizzacafe.domain.entities.Pizza

sealed class MenuViewModelState(
    val selectedSection: MenuSection = MenuSection.Pizza
)

data object LoadingPizzaState: MenuViewModelState()

data class ErrorMenuState(
    val message: String = ""
): MenuViewModelState()

data class DisplayingPizzaState(
    val list: List<Pizza>
): MenuViewModelState()