package com.example.pizzacafe.presentation.adapters.diffUtils

import androidx.recyclerview.widget.DiffUtil
import com.example.pizzacafe.domain.entities.MenuItem

class MenuItemDiffCallback: DiffUtil.ItemCallback<MenuItem>() {
    override fun areItemsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MenuItem, newItem: MenuItem): Boolean {
        return oldItem == newItem
    }
}