package com.example.pizzacafe.domain.interfaces

import com.example.pizzacafe.domain.entities.Category
import com.example.pizzacafe.domain.entities.MenuItem
import org.json.JSONObject

interface JsonMapperInterface {
    fun mapJsonToMenuItemsList(json: JSONObject): List<MenuItem>

    fun mapJsonToCategoriesList(json: JSONObject): List<Category>
}