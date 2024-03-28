package com.example.pizzacafe.data.mappers

import android.graphics.BitmapFactory
import com.example.pizzacafe.domain.entities.Category
import com.example.pizzacafe.domain.entities.MenuItem
import com.example.pizzacafe.domain.interfaces.JsonMapperInterface
import org.json.JSONObject
import java.net.URL
import javax.inject.Inject

class JsonMapperImplementation @Inject constructor(): JsonMapperInterface {

    override fun mapJsonToMenuItemsList(json: JSONObject): List<MenuItem> {
        val list = json.getJSONArray(MENU_ITEMS_LIST_KEY)
        val n = list.length()
        val result = mutableListOf<MenuItem>()
        for (i in 0 until n) {
            val nextElem = list.getJSONObject(i)
            result.add(mapJsonToMenuItem(nextElem))
        }
        return result
    }

    override fun mapJsonToCategoriesList(json: JSONObject): List<Category> {
        val list = json.getJSONArray(CATEGORIES_LIST_KEY)
        val n = list.length()
        val result = mutableListOf<Category>()
        for (i in 0 until n) {
            val nextElem = list.getJSONObject(i)
            result.add(mapJsonToCategory(nextElem))
        }
        return result
    }

    private fun mapJsonToMenuItem(json: JSONObject) = MenuItem(
        id = json.getString(MENU_ITEM_ID_KEY).toInt(),
        name = json.getString(NAME_KEY),
        description = json.getString(DESCRIPTION_KEY),
        categoryName = json.getString(CATEGORY_NAME_KEY),
        image = loadBitmap(json.getString(IMAGE_LINK_KEY))
    )

    private fun mapJsonToCategory(json: JSONObject) = Category(
        id = json.getString(CATEGORY_ID_KEY).toInt(),
        name = json.getString(CATEGORY_NAME_KEY)
    )

    private fun loadBitmap(link: String) = BitmapFactory.decodeStream(URL(link).openStream())

    companion object {
        private const val MENU_ITEMS_LIST_KEY = "meals"
        private const val CATEGORIES_LIST_KEY = "categories"
        private const val MENU_ITEM_ID_KEY = "idMeal"
        private const val CATEGORY_ID_KEY = "idCategory"
        private const val CATEGORY_NAME_KEY = "strCategory"
        private const val NAME_KEY = "strMeal"
        private const val DESCRIPTION_KEY = "strInstructions"
        private const val IMAGE_LINK_KEY = "strMealThumb"
    }
}