package com.example.pizzacafe.di.modules

import com.example.pizzacafe.data.implementations.DataLoaderImplementation
import com.example.pizzacafe.data.implementations.DbModelMapperImplementation
import com.example.pizzacafe.data.implementations.JsonMapperImplementation
import com.example.pizzacafe.domain.interfaces.DataLoaderInterface
import com.example.pizzacafe.domain.interfaces.DbModelMapperInterface
import com.example.pizzacafe.domain.interfaces.JsonMapperInterface
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    fun bindDataLoaderImpl(impl: DataLoaderImplementation): DataLoaderInterface

    @Binds
    fun bindJsonMapperImpl(impl: JsonMapperImplementation): JsonMapperInterface

    @Binds
    fun bindDbModelMapperInterface(impl: DbModelMapperImplementation): DbModelMapperInterface
}