package com.example.pizzacafe.data.mappers

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.pizzacafe.data.dbModels.CategoryDbModel
import com.example.pizzacafe.data.dbModels.MenuItemDbModel
import com.example.pizzacafe.domain.entities.Category
import com.example.pizzacafe.domain.entities.MenuItem
import com.example.pizzacafe.domain.interfaces.DbModelMapperInterface
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class DbModelMapperImplementation @Inject constructor(): DbModelMapperInterface {
    override fun mapMenuItemToDbModel(item: MenuItem) = MenuItemDbModel(
        id = item.id,
        name = item.name,
        categoryName = item.categoryName,
        price = item.price,
        description = item.description,
        image = mapBitmapToByteArray(item.image)
    )

    override fun mapCategoryToDbModel(item: Category) = CategoryDbModel(
        id = item.id,
        name = item.name
    )

    override fun mapToMenuItem(item: MenuItemDbModel) = MenuItem(
        id = item.id,
        name = item.name,
        price = item.price,
        description = item.description,
        categoryName = item.categoryName,
        image = mapByteArrayToBitmap(item.image)
    )

    override fun mapToCategory(item: CategoryDbModel) = Category(
        id = item.id,
        name = item.name
    )

    private fun mapByteArrayToBitmap(a: ByteArray) = try {
        BitmapFactory.decodeByteArray(a, 0, a.size)
    } catch (e: Exception) {
        null
    }

    private fun mapBitmapToByteArray(bitmap: Bitmap?): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 90, stream)
        return stream.toByteArray()
    }
}