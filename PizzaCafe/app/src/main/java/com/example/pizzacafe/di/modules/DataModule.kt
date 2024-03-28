package com.example.pizzacafe.di.modules

import android.app.Application
import com.example.pizzacafe.data.AppDataBase
import com.example.pizzacafe.data.FoodDaoInterface
import com.example.pizzacafe.di.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class DataModule {
    @ApplicationScope
    @Provides
    fun provideFoodDao(application: Application): FoodDaoInterface {
        return AppDataBase.getInstance(application).getFoodDao()
    }
}