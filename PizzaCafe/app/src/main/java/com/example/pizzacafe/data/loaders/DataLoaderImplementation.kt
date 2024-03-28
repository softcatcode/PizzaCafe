package com.example.pizzacafe.data.loaders

import android.graphics.BitmapFactory
import com.example.pizzacafe.domain.entities.Banner
import com.example.pizzacafe.domain.entities.Category
import com.example.pizzacafe.domain.entities.MenuItem
import com.example.pizzacafe.domain.interfaces.DataLoaderInterface
import com.example.pizzacafe.domain.interfaces.JsonMapperInterface
import org.json.JSONObject
import java.net.URL

class DataLoaderImplementation(
    private val jsonMapper: JsonMapperInterface
): DataLoaderInterface {

//    private val networkCallback = object: ConnectivityManager.NetworkCallback() {
//
//        override fun onAvailable(network: Network) {
//            super.onAvailable(network)
//            isNetworkAvailable = true
//        }
//
//        override fun onLost(network: Network) {
//            super.onLost(network)
//            isNetworkAvailable = false
//        }
//    }

//    init {
//        val connectivityManager = getSystemService(application, ConnectivityManager::class.java)
//        connectivityManager?.registerDefaultNetworkCallback(networkCallback)
//    }

    override suspend fun getMenuItemsList(category: String): List<MenuItem> {
        val text = URL(MENU_ITEMS_LINK).readText()
        val data = JSONObject(text)
        return jsonMapper.mapJsonToMenuItemsList(data).filter { it.categoryName == category }
    }

    override suspend fun getCategoryList(): List<Category> {
        val text = URL(CATEGORIES_LINK).readText()
        val data = JSONObject(text)
        return jsonMapper.mapJsonToCategoriesList(data)
    }

    private fun loadBanner(link: String): Banner {
        val stream = URL(link).openStream()
        return Banner(BitmapFactory.decodeStream(stream))
    }

    override suspend fun getBannerList() = listOf(loadBanner(RED_BANNER_LINK), loadBanner(ORANGE_BANNER_LINK))

    companion object {
        private const val MENU_ITEMS_LINK = "https://themealdb.com/api/json/v1/1/search.php?s"
        private const val CATEGORIES_LINK = "https://themealdb.com/api/json/v1/1/categories.php"
        private const val RED_BANNER_LINK = "https://s3-alpha-sig.figma.com/img/0e5f/8769/bbdcde65e7e95920e9f71a180817df1a?Expires=1712534400&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=AcQGBqdjDyApa~QlwjYTE~VBB6je0Bt9ptRSRy2VwgAq0GVbMMkkJngrUTM6ndXIn6etJj-ezqRv3D~RpKX~LnNNwvQ~CVQiOnRAJx3fLGkIyfA~zfkRq4qGhZBMwH6sp2ae-K1ciDgYnSwCf34~-mhu0FsY2-OZTL2adZ8nMqjkEgO~G4xmRwJNwNuW2p9jFliGL4Fiuw6NfZCJcdYs36rFbwRVAR7Dsk7XBHFvsSYzKmRL1nzFkyu-gjTgYcubJ4Tai~kryAo~ynccgW32gPVgAMUnK8OJRKuT60SMhhRkBYCUxiIKt4ttwgl5XfSRzwHg73tx~UmWWrQU~MtXIg"
        private const val ORANGE_BANNER_LINK = "https://s3-alpha-sig.figma.com/img/b42b/b92c/f6acf7e8e259819d3dd44499cb49eb54?Expires=1712534400&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=W8WKOkVOuL5lYZEOvk64LsnEusAN2PrjVpKXvfl-DZV9~VODo6ZsyvP9e0uQltDEs5lirr64UxO1YJ4iW03SYs5u5mYaRuni6ka5bVEyR3DPqkzQj8Frx2fP~0RZznD-cLV4tK0F2L3KT8Go6HFHqLXSkkVULjDub-9p3c~FMqFE-fWZCEtF44e9fDAWp8uFW3uegC~DGVylcTzTV9qGJzgnhhwUtR3DTTJ8el7yNXW9d0pQtZkeSz~~v6ua5zV9q9~Hxl3eB4Mw9MNHl9MERJYp8XB-8vEIrfxUSDKjlx47-K714plq-DWeTLy-9WWaDj0y0vTOXkYPp2nCK9MRtQ"
    }
}