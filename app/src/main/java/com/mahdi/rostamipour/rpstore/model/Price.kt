package com.mahdi.rostamipour.rpstore.model

import kotlinx.serialization.Serializable

@Serializable
data class Price(
    val maxPrice: String,
    val minPrice: String
)