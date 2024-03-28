package com.example.pizzacafe.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pizzacafe.di.ApplicationScope
import javax.inject.Inject
import javax.inject.Provider

@Suppress("UNCHECKED_CAST")
@ApplicationScope
class ViewModelFactory @Inject constructor(
    private val providers: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) = providers[modelClass]?.get() as T
}