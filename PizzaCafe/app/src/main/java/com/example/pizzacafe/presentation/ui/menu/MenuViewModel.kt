package com.example.pizzacafe.presentation.ui.menu

import android.app.Application
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pizzacafe.R
import com.example.pizzacafe.domain.useCases.LoadPizzaListUseCase
import com.example.pizzacafe.presentation.ui.state.DisplayingMenuItemsState
import com.example.pizzacafe.presentation.ui.state.ErrorState
import com.example.pizzacafe.presentation.ui.state.LoadingMenuItemsState
import com.example.pizzacafe.presentation.ui.state.MenuViewModelState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject

class MenuViewModel @Inject constructor(
    private val loadPizzaListUseCase: LoadPizzaListUseCase,
    application: Application
): AndroidViewModel(application) {

    private val _state = MutableLiveData<MenuViewModelState>()
    val state: LiveData<MenuViewModelState>
        get() = _state

    private var timer = Timer()

    private fun processInternetError(category: MenuSection) {
        timer = Timer().apply{
            schedule(
                object: TimerTask() {
                    override fun run() {
                        load(category)
                    }
                },
                RETRY_TIME_GAP
            )
        }
    }

    private fun load(category: MenuSection) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _state.value = state.value?.copy(menuState = LoadingMenuItemsState)
            }
            try {
                val pizzaList = loadPizzaListUseCase(category)
                _state.postValue(
                    state.value?.copy(menuState = DisplayingMenuItemsState(pizzaList))
                )
            } catch (e: IOException) {
                processInternetError(category)
                val msg = ContextCompat.getString(getApplication(), R.string.internet_error_message)
                _state.postValue(
                    state.value?.copy(menuState = ErrorState(msg))
                )
            } catch (e: Exception) {
                val msg = ContextCompat.getString(getApplication(), R.string.other_error_message)
                _state.postValue(
                    state.value?.copy(menuState = ErrorState(msg))
                )
            }
        }
    }

    companion object {
        private const val RETRY_TIME_GAP = 5000L
    }
}