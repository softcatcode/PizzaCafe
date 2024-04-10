package com.example.pizzacafe.data.dbModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CategoryDbModel.tableName)
class CategoryDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val name: String
) {
    companion object {
        const val tableName = "category_table"
    }
}