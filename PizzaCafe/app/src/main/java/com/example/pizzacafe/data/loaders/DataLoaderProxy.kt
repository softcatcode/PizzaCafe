package com.example.pizzacafe.data.loaders

import com.example.pizzacafe.data.FoodDaoInterface
import com.example.pizzacafe.domain.entities.Category
import com.example.pizzacafe.domain.entities.MenuItem
import com.example.pizzacafe.domain.interfaces.DataLoaderInterface
import com.example.pizzacafe.domain.interfaces.DbModelMapperInterface
import com.example.pizzacafe.domain.interfaces.JsonMapperInterface
import java.util.Calendar
import javax.inject.Inject

class DataLoaderProxy @Inject constructor(
    private val jsonMapper: JsonMapperInterface,
    private val dbModelMapper: DbModelMapperInterface,
    private val foodDao: FoodDaoInterface
): DataLoaderInterface {

    private var lastLoadFoodTime = 0L
    private var lastLoadCategoriesTime = 0L

    override suspend fun getMenuItemsList(category: String): List<MenuItem> {
        val currentTime = Calendar.getInstance().timeInMillis
        return if (currentTime - lastLoadFoodTime > FOOD_DATA_ACTUALITY_TIME_MILLIS) {
            try {
                val result = DataLoaderImplementation(jsonMapper).getMenuItemsList(category)
                lastLoadFoodTime = currentTime
                result
            } catch (_: Exception) {
                getFoodFromDatabase(category)
            }
        } else
            getFoodFromDatabase(category)
    }

    private suspend fun getFoodFromDatabase(category: String): List<MenuItem> {
        return foodDao.getMenuItems(category).filter {
            it.name == category
        }.map {
            dbModelMapper.mapToMenuItem(it)
        }
    }

    private suspend fun getCategoriesFromDatabase(): List<Category> {
        return foodDao.getCategories().map {
            dbModelMapper.mapToCategory(it)
        }
    }

    override suspend fun getCategoryList(): List<Category> {
        val currentTime = Calendar.getInstance().timeInMillis
        return if (currentTime - lastLoadCategoriesTime > CATEGORIES_DATA_ACTUALITY_TIME_MILLIS) {
            try {
                val result = DataLoaderImplementation(jsonMapper).getCategoryList()
                lastLoadFoodTime = currentTime
                result
            } catch (_: Exception) {
                getCategoriesFromDatabase()
            }
        } else
            getCategoriesFromDatabase()
    }

    override suspend fun getBannerList() = DataLoaderImplementation(jsonMapper).getBannerList()

    companion object {
        private const val FOOD_DATA_ACTUALITY_TIME_MILLIS = 60000L
        private const val CATEGORIES_DATA_ACTUALITY_TIME_MILLIS = 100000L
    }
}