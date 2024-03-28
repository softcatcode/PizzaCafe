package com.example.pizzacafe.presentation.ui.state

import com.example.pizzacafe.domain.entities.Banner

data class ToolBarState(
    val firstBanner: Banner? = null,
    val secondBanner: Banner? = null
)