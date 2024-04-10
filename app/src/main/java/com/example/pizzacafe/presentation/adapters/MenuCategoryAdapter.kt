package com.example.pizzacafe.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.pizzacafe.R
import com.example.pizzacafe.domain.entities.Category
import com.example.pizzacafe.presentation.adapters.diffUtils.CategoryDiffCallback
import com.example.pizzacafe.presentation.adapters.viewHolders.CategoryViewHolder

class MenuCategoryAdapter: ListAdapter<Category, CategoryViewHolder>(CategoryDiffCallback()) {

    var onClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val id = when (viewType) {
            ENABLED_TYPE -> R.layout.categoty_card_enabled
            DISABLED_TYPE -> R.layout.categoty_card_disabled
            else -> throw RuntimeException("Unknown view type in category recycler view")
        }
        val view = LayoutInflater.from(parent.context)
            .inflate(id, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.textView.text = item.name
        holder.categoryId = item.id
        holder.view.setOnClickListener {
            onClickListener?.invoke(holder.categoryId)
        }
    }

    override fun getItemViewType(position: Int) =
        when (getItem(position).enabled) {
            true -> ENABLED_TYPE
            else -> DISABLED_TYPE
        }

    companion object {
        const val ENABLED_TYPE = 1
        const val DISABLED_TYPE = 2
    }
}