package com.example.pizzacafe.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pizzacafe.data.dbModels.CategoryDbModel
import com.example.pizzacafe.data.dbModels.MenuItemDbModel

@Dao
interface FoodDaoInterface {
    @Query("select * from ${MenuItemDbModel.tableName} where categoryName = :categoryName")
    suspend fun getMenuItems(categoryName: String): List<MenuItemDbModel>

    @Query("select * from ${CategoryDbModel.tableName} where 1")
    suspend fun getCategories(): List<CategoryDbModel>

    @Query("delete from ${MenuItemDbModel.tableName} where 1")
    suspend fun clearMenuItems()

    @Query("delete from ${CategoryDbModel.tableName} where 1")
    suspend fun clearCategories()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMenuItem(item: MenuItemDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategory(item: CategoryDbModel)
}