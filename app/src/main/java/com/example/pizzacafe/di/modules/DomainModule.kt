package com.example.pizzacafe.di.modules

import com.example.pizzacafe.data.loaders.DataLoaderProxy
import com.example.pizzacafe.data.mappers.DbModelMapperImplementation
import com.example.pizzacafe.data.mappers.JsonMapperImplementation
import com.example.pizzacafe.domain.interfaces.DataLoaderInterface
import com.example.pizzacafe.domain.interfaces.DbModelMapperInterface
import com.example.pizzacafe.domain.interfaces.JsonMapperInterface
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    fun bindDataLoaderImpl(impl: DataLoaderProxy): DataLoaderInterface

    @Binds
    fun bindJsonMapperImpl(impl: JsonMapperImplementation): JsonMapperInterface

    @Binds
    fun bindDbModelMapperInterface(impl: DbModelMapperImplementation): DbModelMapperInterface
}