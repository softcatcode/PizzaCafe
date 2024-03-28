package com.example.pizzacafe.data.loaders

import com.example.pizzacafe.data.FoodDaoInterface
import com.example.pizzacafe.domain.entities.Category
import com.example.pizzacafe.domain.entities.MenuItem
import com.example.pizzacafe.domain.interfaces.DataLoaderInterface
import com.example.pizzacafe.domain.interfaces.DbModelMapperInterface
import com.example.pizzacafe.domain.interfaces.JsonMapperInterface
import java.io.IOException
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
        if (currentTime - lastLoadFoodTime > FOOD_DATA_ACTUALITY_TIME_MILLIS) {
            val status = updateMenuItemsData()
            val result = getMenuItemsFromDatabase(category)
            if (!status && result.isEmpty())
                throw IOException("No internet and the database does not have the data.")
            lastLoadFoodTime = currentTime
            return result
        }
        return getMenuItemsFromDatabase(category)
    }

    override suspend fun getCategoryList(): List<Category> {
        val currentTime = Calendar.getInstance().timeInMillis
        if (currentTime - lastLoadCategoriesTime > CATEGORIES_DATA_ACTUALITY_TIME_MILLIS) {
            val status = updateCategoriesData()
            val result = getCategoriesFromDatabase()
            if (!status && result.isEmpty())
                throw IOException("No internet and the database does not have the data.")
            lastLoadCategoriesTime = currentTime
            return result
        }
        return getCategoriesFromDatabase()
    }

    override suspend fun getAllMenuItems() = DataLoaderImplementation(jsonMapper).getAllMenuItems()

    override suspend fun getBannerList() = DataLoaderImplementation(jsonMapper).getBannerList()

    private suspend inline fun getMenuItemsFromDatabase(category: String) =
        foodDao.getMenuItems(category).map { dbModelMapper.mapToMenuItem(it) }

    private suspend inline fun getCategoriesFromDatabase() =
        foodDao.getCategories().map { dbModelMapper.mapToCategory(it) }

    private suspend fun updateMenuItemsData() = try {
        val result = getAllMenuItems()
        for (elem in result)
            foodDao.addMenuItem(dbModelMapper.mapMenuItemToDbModel(elem))
        true
    } catch (_: Exception) {
        false
    }

    private suspend fun updateCategoriesData() = try {
        val result = DataLoaderImplementation(jsonMapper).getCategoryList()
        for (elem in result)
            foodDao.addCategory(dbModelMapper.mapCategoryToDbModel(elem))
        true
    } catch (_: Exception) {
        false
    }

    companion object {
        private const val FOOD_DATA_ACTUALITY_TIME_MILLIS = 60000L
        private const val CATEGORIES_DATA_ACTUALITY_TIME_MILLIS = 100000L
    }
}