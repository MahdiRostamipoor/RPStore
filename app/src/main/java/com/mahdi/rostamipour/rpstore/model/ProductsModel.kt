package com.mahdi.rostamipour.rpstore.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductsModel(
    val category: Int,
    val description: String,
    val id: Int,
    val offer: Boolean,
    val picture: String,
    val price: Int,
    val priceOffer: Int?,
    val title: String
)