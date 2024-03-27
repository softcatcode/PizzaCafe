package com.example.pizzacafe.presentation.adapters.viewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.pizzacafe.R
import com.example.pizzacafe.domain.entities.Category

class CategoryViewHolder(val view: View): ViewHolder(view) {
    var categoryId: Int = Category.UNDEFINED_ID
    val textView: TextView = view.findViewById(R.id.name)
}