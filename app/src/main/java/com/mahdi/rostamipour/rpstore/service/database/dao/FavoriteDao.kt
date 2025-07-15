package com.mahdi.rostamipour.rpstore.service.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mahdi.rostamipour.rpstore.model.FavoriteModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favoriteProducts")
    fun getAllFavoriteProducts(): Flow<List<FavoriteModel>>

    @Query("SELECT EXISTS(SELECT 1 FROM favoriteProducts WHERE id = :productId)")
    fun getFavoriteProductById(productId: Int): Flow<Boolean>

    @Insert
    suspend fun insertFavoriteProduct(product: FavoriteModel)

    @Query("DELETE FROM favoriteProducts WHERE id = :productId")
    suspend fun deleteFavoriteProductById(productId: Int)

}