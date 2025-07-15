package com.mahdi.rostamipour.rpstore.model.repository

import com.mahdi.rostamipour.rpstore.service.ApiService

class CommentsRepository(val apiService: ApiService) {
    suspend fun getComments(productId : Int) = apiService.getComments(productId)
}