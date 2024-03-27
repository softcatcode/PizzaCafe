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
import com.example.pizzacafe.domain.useCases.LoadMenuItemsUseCase
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
    private val loadBannersUseCase: LoadBannersUseCase,
    application: Application
): AndroidViewModel(application) {

    private val _menuState = MutableLiveData<MenuState>()
    val menuState: LiveData<MenuState>
        get() = _menuState

    private val _toolBarState = MutableLiveData<ToolBarState>()
    val toolBarState: LiveData<ToolBarState>
        get() = _toolBarState

    private var timer = Timer()

    init {
        loadMenu(MenuSection.Pizza)
        loadBanners()
    }

    private fun processMenuLoadError(category: MenuSection) {
        timer = Timer().apply{
            schedule(
                object: TimerTask() {
                    override fun run() {
                        loadMenu(category)
                    }
                },
                RETRY_TIME_GAP
            )
        }
    }

    fun loadMenu(category: MenuSection) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _menuState.value = LoadingMenuItemsState(category)
            }
            try {
                val pizzaList = loadMenuItemsUseCase(category)
                _menuState.postValue(DisplayingMenuItemsState(pizzaList, category))
            } catch (e: IOException) {
                processMenuLoadError(category)
                val msg = ContextCompat.getString(getApplication(), R.string.internet_error_message)
                _menuState.postValue(ErrorState(msg, category))
            } catch (e: Exception) {
                val msg = ContextCompat.getString(getApplication(), R.string.other_error_message)
                _menuState.postValue(ErrorState(msg, category))
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

    companion object {
        private const val RETRY_TIME_GAP = 5000L
    }
}