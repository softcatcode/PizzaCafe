package com.example.pizzacafe.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.pizzacafe.R
import com.example.pizzacafe.domain.entities.MenuItem
import com.example.pizzacafe.presentation.adapters.diffUtils.MenuItemDiffCallback
import com.example.pizzacafe.presentation.adapters.viewHolders.MenuItemViewHolder

class MenuItemListAdapter: ListAdapter<MenuItem, MenuItemViewHolder>(MenuItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pizza_card, parent, false)
        return MenuItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.name.text = item.name
        holder.description.text = item.description
        holder.price.text = item.price.toString()
    }

    override fun getItemViewType(position: Int): Int {
        return PIZZA_VIEW_TYPE
    }

    companion object {
        const val PIZZA_VIEW_TYPE = 1
    }
}