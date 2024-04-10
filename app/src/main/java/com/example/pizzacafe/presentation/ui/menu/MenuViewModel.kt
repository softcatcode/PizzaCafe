package com.example.pizzacafe.presentation.ui.menu

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pizzacafe.R
import com.example.pizzacafe.domain.entities.Banner
import com.example.pizzacafe.domain.useCases.LoadBannersUseCase
import com.example.pizzacafe.domain.useCases.LoadCategoriesUseCase
import com.example.pizzacafe.domain.useCases.LoadMenuItemsUseCase
import com.example.pizzacafe.presentation.ui.state.CategoryState
import com.example.pizzacafe.presentation.ui.state.DisplayingMenuItemsState
import com.example.pizzacafe.presentation.ui.state.ErrorState
import com.example.pizzacafe.presentation.ui.state.LoadingMenuItemsState
import com.example.pizzacafe.presentation.ui.state.MenuState
import com.example.pizzacafe.presentation.ui.state.ToolBarState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject

class MenuViewModel @Inject constructor(
    private val loadMenuItemsUseCase: LoadMenuItemsUseCase,
    private val loadCategoriesUseCase: LoadCategoriesUseCase,
    private val loadBannersUseCase: LoadBannersUseCase,
    application: Application
): AndroidViewModel(application) {

    private val _menuState = MutableLiveData<MenuState>()
    val menuState: LiveData<MenuState>
        get() = _menuState

    private val _categoryState = MutableLiveData<CategoryState>()
    val categoryState: LiveData<CategoryState>
        get() = _categoryState

    private val _toolBarState = MutableLiveData<ToolBarState>()
    val toolBarState: LiveData<ToolBarState>
        get() = _toolBarState

    private var reloadingTimer = Timer()

    init {
        loadBanners()
        loadCategories()
    }
    
    fun selectCategory(id: Int) {
        val categoryList = categoryState.value?.list?.map {
            it.copy(enabled = it.id == id)
        } ?: return
        resetReloadTimer()
        _categoryState.value = CategoryState(categoryList)
        loadMenu(id)
    }
    
    private fun resetReloadTimer() {
        reloadingTimer.cancel()
        reloadingTimer = Timer()
    }

    private fun processCategoriesLoadError() {
        resetReloadTimer()
        reloadingTimer.schedule(
            object: TimerTask() {
                override fun run() {
                    loadCategories()
                }
            },
            RETRY_TIME_GAP
        )
    }

    private fun loadCategories() {
        resetReloadTimer()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val categories = loadCategoriesUseCase()
                withContext(Dispatchers.Main) {
                    _categoryState.value = CategoryState(categories)
                    if (categories.isNotEmpty())
                        selectCategory(categories[0].id)
                }
            } catch (e: Exception) {
                processCategoriesLoadError()
            }
        }
    }

    private fun processMenuLoadError(categoryId: Int) {
        reloadingTimer.schedule(
            object: TimerTask() {
                override fun run() {
                    loadMenu(categoryId)
                }
            },
            RETRY_TIME_GAP
        )
    }

    private fun loadMenu(categoryId: Int) {
        val categories = categoryState.value?.list ?: mutableListOf()
        val categoryName = categories.find { it.id == categoryId }?.name ?: return

        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _menuState.value = LoadingMenuItemsState
            }
            try {
                val foodList = loadMenuItemsUseCase(categoryName)
                _menuState.postValue(DisplayingMenuItemsState(foodList))
            } catch (e: IOException) {
                processMenuLoadError(categoryId)
                val msg = ContextCompat.getString(getApplication(), R.string.internet_error_message)
                _menuState.postValue(ErrorState(msg))
            } catch (e: Exception) {
                val msg = ContextCompat.getString(getApplication(), R.string.other_error_message)
                _menuState.postValue(ErrorState(msg))
            }
        }
    }

    private fun loadBanners() {
        viewModelScope.launch(Dispatchers.IO) {
            val banners = loadBannersUseCase()
            var first: Banner? = null
            var second: Banner? = null
            if (banners.isNotEmpty())
                first = banners[0]
            if (banners.size > 1)
                second = banners[1]
            _toolBarState.postValue(ToolBarState(first, second))
        }
    }

    override fun onCleared() {
        super.onCleared()
        reloadingTimer.cancel()
    }

    companion object {
        private const val RETRY_TIME_GAP = 5000L
    }
}