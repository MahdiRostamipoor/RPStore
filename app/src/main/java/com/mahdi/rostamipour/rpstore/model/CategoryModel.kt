package com.mahdi.rostamipour.rpstore.model

@kotlinx.serialization.Serializable
data class CategoryModel(
    val id: Int,
    val picture: String,
    val title: String
)