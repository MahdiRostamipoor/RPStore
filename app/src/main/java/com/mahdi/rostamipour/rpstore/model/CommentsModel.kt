package com.mahdi.rostamipour.rpstore.model

import kotlinx.serialization.Serializable

@Serializable
data class CommentsModel(
    val comment: String,
    val date: String,
    val id: Int,
    val productId: Int,
    val userName: String
)