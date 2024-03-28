package com.example.pizzacafe.domain.useCases

import com.example.pizzacafe.domain.entities.Banner
import com.example.pizzacafe.domain.interfaces.DataLoaderInterface
import javax.inject.Inject

class LoadBannersUseCase @Inject constructor(
    private val repository: DataLoaderInterface
) {
    suspend operator fun invoke(): List<Banner> {
        return repository.getBannerList()
    }
}