package com.example.pizzacafe.data.dbModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = MenuItemDbModel.tableName)
class MenuItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val name: String,
    val price: Int,
    val description: String,
    val categoryName: String,
    val image: ByteArray
) {
    companion object {
        const val tableName = "menu_item_table"
    }
}