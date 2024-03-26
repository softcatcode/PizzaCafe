package com.example.pizzacafe.presentation.ui.state

data class MenuViewModelState(
    val toolBarState: ToolBarState = ToolBarState(),
    val menuState: MenuState
)