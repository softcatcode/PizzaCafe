package com.example.pizzacafe.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pizzacafe.data.dbModels.CategoryDbModel
import com.example.pizzacafe.data.dbModels.MenuItemDbModel

@Database(
    entities = [CategoryDbModel::class, MenuItemDbModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase: RoomDatabase() {

    abstract fun getFoodDao(): FoodDaoInterface

    companion object {
        private val LOCK = Any()
        private const val DB_NAME = "FoodDataBase"
        private var INSTANCE: AppDataBase? = null

        fun getInstance(application: Application): AppDataBase {
            INSTANCE?.let { return it }
            synchronized(LOCK) {
                INSTANCE?.let { return it }
                val db = Room.databaseBuilder(application, AppDataBase::class.java, DB_NAME).build()
                INSTANCE = db
                return db
            }
        }
    }
}