package com.example.pizzacafe.presentation.adapters.diffUtils

import androidx.recyclerview.widget.DiffUtil
import com.example.pizzacafe.domain.entities.Category

class CategoryDiffCallback: DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}