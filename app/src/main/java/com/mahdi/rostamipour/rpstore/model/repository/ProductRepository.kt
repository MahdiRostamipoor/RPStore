package com.mahdi.rostamipour.rpstore.model.repository

import com.mahdi.rostamipour.rpstore.service.ApiService

class ProductRepository(val apiService: ApiService) {

    suspend fun getProducts() = apiService.getProducts()

}