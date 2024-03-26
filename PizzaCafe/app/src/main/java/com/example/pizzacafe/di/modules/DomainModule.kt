package com.example.pizzacafe.di.modules

import com.example.pizzacafe.data.implementations.DataLoaderImplementation
import com.example.pizzacafe.domain.interfaces.DataLoaderInterface
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    fun bindDataLoaderImpl(impl: DataLoaderImplementation): DataLoaderInterface
}