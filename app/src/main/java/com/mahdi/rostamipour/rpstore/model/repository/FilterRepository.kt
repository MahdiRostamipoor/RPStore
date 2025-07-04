package com.mahdi.rostamipour.rpstore.model.repository

import com.mahdi.rostamipour.rpstore.service.ApiService

class FilterRepository(val apiService: ApiService) {

    suspend fun getProductByCategory(categoryId : Int) = apiService.getProductsByCategory(categoryId)

    suspend fun getFilteringItems() = apiService.getFilteringItems()

}