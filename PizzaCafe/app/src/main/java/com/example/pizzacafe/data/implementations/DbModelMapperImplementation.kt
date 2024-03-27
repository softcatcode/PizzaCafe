package com.example.pizzacafe.data.implementations

import android.graphics.Bitmap
import com.example.pizzacafe.data.dbModels.CategoryDbModel
import com.example.pizzacafe.data.dbModels.MenuItemDbModel
import com.example.pizzacafe.domain.entities.Category
import com.example.pizzacafe.domain.entities.MenuItem
import com.example.pizzacafe.domain.interfaces.DbModelMapperInterface
import java.io.ByteArrayOutputStream

class DbModelMapperImplementation: DbModelMapperInterface {
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

    private fun mapBitmapToByteArray(bitmap: Bitmap?): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 90, stream)
        return stream.toByteArray()
    }
}