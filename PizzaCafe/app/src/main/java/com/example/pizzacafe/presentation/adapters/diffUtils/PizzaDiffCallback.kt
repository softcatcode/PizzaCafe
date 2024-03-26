package com.example.pizzacafe.presentation.adapters.diffUtils

import androidx.recyclerview.widget.DiffUtil
import com.example.pizzacafe.domain.entities.Pizza

class PizzaDiffCallback: DiffUtil.ItemCallback<Pizza>() {
    override fun areItemsTheSame(oldItem: Pizza, newItem: Pizza): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Pizza, newItem: Pizza): Boolean {
        return oldItem == newItem
    }
}