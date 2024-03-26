package com.example.pizzacafe.presentation.adapters.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.pizzacafe.R

class PizzaViewHolder(view: View): ViewHolder(view) {
    val name = view.findViewById<TextView>(R.id.pizza_name)
    val description = view.findViewById<TextView>(R.id.pizza_description)
    val price = view.findViewById<TextView>(R.id.pizza_price)
    val image = view.findViewById<ImageView>(R.id.pizza_image)
}