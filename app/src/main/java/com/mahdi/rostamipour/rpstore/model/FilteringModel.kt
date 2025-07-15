package com.mahdi.rostamipour.rpstore.model

import kotlinx.serialization.Serializable

@Serializable
data class FilteringModel(
    val Brand: List<Brand>,
    val Color: List<Color>,
    val Price: Price
)