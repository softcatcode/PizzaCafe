package com.example.pizzacafe.presentation.ui.state

import com.example.pizzacafe.domain.entities.Category
import com.example.pizzacafe.domain.entities.MenuItem

sealed class MenuState

data object LoadingMenuItemsState : MenuState()

data class ErrorState(
    val message: String = ""
): MenuState()

data class DisplayingMenuItemsState(
    val list: List<MenuItem>
): MenuState()