package com.mahdi.rostamipour.rpstore.intent.state

import com.mahdi.rostamipour.rpstore.model.FavoriteModel

data class FavoriteState (
    val isLoading : Boolean = false ,
    val success : List<FavoriteModel> = emptyList() ,
    val favoriteStatus: Map<Int, Boolean> = emptyMap(),
    val error : String? = null
)