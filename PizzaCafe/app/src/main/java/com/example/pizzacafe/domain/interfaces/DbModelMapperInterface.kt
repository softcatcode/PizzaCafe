package com.example.pizzacafe.domain.interfaces

import com.example.pizzacafe.data.dbModels.CategoryDbModel
import com.example.pizzacafe.data.dbModels.MenuItemDbModel
import com.example.pizzacafe.domain.entities.Category
import com.example.pizzacafe.domain.entities.MenuItem

interface DbModelMapperInterface {

    fun mapMenuItemToDbModel(item: MenuItem): MenuItemDbModel

    fun mapCategoryToDbModel(item: Category): CategoryDbModel

    fun mapToMenuItem(item: MenuItemDbModel): MenuItem

    fun mapToCategory(item: CategoryDbModel): Category
}