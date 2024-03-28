package com.example.pizzacafe.di.modules

import androidx.lifecycle.ViewModel
import com.example.pizzacafe.presentation.ui.bucket.BucketViewModel
import com.example.pizzacafe.presentation.ui.menu.MenuViewModel
import com.example.pizzacafe.presentation.ui.profile.ProfileViewModel
import com.example.pizzacafe.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MenuViewModel::class)
    @Binds
    fun bindMenuViewModel(impl: MenuViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    @Binds
    fun bindProfileViewModel(impl: ProfileViewModel): ViewModel

    @IntoMap
    @ViewModelKey(BucketViewModel::class)
    @Binds
    fun bindBucketViewModel(impl: BucketViewModel): ViewModel
}