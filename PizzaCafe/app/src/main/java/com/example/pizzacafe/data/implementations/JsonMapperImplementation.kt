package com.example.pizzacafe.data.implementations

import android.graphics.BitmapFactory
import com.example.pizzacafe.domain.entities.MenuItem
import com.example.pizzacafe.domain.interfaces.JsonMapperInterface
import org.json.JSONObject
import java.net.URL
import javax.inject.Inject
import kotlin.math.min

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

    private fun mapJsonToMenuItem(json: JSONObject) = MenuItem(
        id = json.getString(ID_KEY).toInt(),
        name = json.getString(NAME_KEY),
        description = json.getString(DESCRIPTION_KEY),
        //category = json.getString(CATEGORY_KEY),
        image = loadBitmap(json.getString(IMAGE_LINK_KEY))
    )

    private fun loadBitmap(link: String) = BitmapFactory.decodeStream(URL(link).openStream())

    companion object {
        private const val MENU_ITEMS_LIST_KEY = "meals"
        private const val ID_KEY = "idMeal"
        private const val CATEGORY_KEY = "strCategory"
        private const val NAME_KEY = "strMeal"
        private const val DESCRIPTION_KEY = "strInstructions"
        private const val IMAGE_LINK_KEY = "strMealThumb"
    }
}