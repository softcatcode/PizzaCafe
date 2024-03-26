package com.example.pizzacafe.presentation.ui.state

import android.media.Image
import com.example.pizzacafe.presentation.ui.menu.MenuSection

data class ToolBarState(
    val availableCities: List<String> = mutableListOf(),
    val firstImage: Image? = null,
    val secondImage: Image? = null
)