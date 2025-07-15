package com.mahdi.rostamipour.rpstore.model.repository

import androidx.lifecycle.LiveData
import com.mahdi.rostamipour.rpstore.model.FavoriteModel
import com.mahdi.rostamipour.rpstore.service.database.dao.FavoriteDao
import kotlinx.coroutines.flow.Flow

class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    suspend fun addProduct(product: FavoriteModel) {
        favoriteDao.insertFavoriteProduct(product)
    }

    fun getProducts(): Flow<List<FavoriteModel>> = favoriteDao.getAllFavoriteProducts()

    suspend fun deleteProduct(productId: Int) {
        favoriteDao.deleteFavoriteProductById(productId)
    }

   // suspend fun getProductById(productId: Int): FavoriteModel = favoriteDao.getFavoriteProductById(productId)

    suspend fun isProductFavorite(productId: Int): Flow<Boolean> {
        return favoriteDao.getFavoriteProductById(productId)
    }

}