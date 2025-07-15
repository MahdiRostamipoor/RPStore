package com.mahdi.rostamipour.rpstore.model.repository

import com.mahdi.rostamipour.rpstore.service.ApiService

class ProfileRepository(val apiService: ApiService) {
    suspend fun getProfile() = apiService.getProfile()
}