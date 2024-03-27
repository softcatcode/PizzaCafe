package com.example.pizzacafe.presentation.ui.state

import com.example.pizzacafe.domain.entities.MenuItem
import com.example.pizzacafe.presentation.ui.menu.MenuSection

sealed class MenuState(
    val category: MenuSection = MenuSection.Pizza
)

class LoadingMenuItemsState(category: MenuSection): MenuState(category)

class ErrorState(
    val message: String = "",
    category: MenuSection
): MenuState(category)

class DisplayingMenuItemsState(
    val list: List<MenuItem>,
    category: MenuSection
): MenuState(category)