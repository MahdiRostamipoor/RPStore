package com.mahdi.rostamipour.rpstore.intent

import com.mahdi.rostamipour.rpstore.model.FavoriteModel

sealed class FavoriteIntent {

    object LoadFavoriteProducts : FavoriteIntent()
    data class AddFavoriteProduct(val favoriteModel: FavoriteModel) : FavoriteIntent()
    data class DeleteFavoriteProduct(val idProduct: Int) : FavoriteIntent()
    object IsProductFavorite : FavoriteIntent()
    data class CheckFavoriteStatus(val productId: Int) : FavoriteIntent()

}