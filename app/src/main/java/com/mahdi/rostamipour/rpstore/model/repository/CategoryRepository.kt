package com.mahdi.rostamipour.rpstore.model.repository

import com.mahdi.rostamipour.rpstore.service.ApiService

class CategoryRepository(val apiService: ApiService) {
    suspend fun getCategories() = apiService.getCategories()
}