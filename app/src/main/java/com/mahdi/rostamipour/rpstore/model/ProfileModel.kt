package com.mahdi.rostamipour.rpstore.model

import kotlinx.serialization.Serializable

@Serializable
data class ProfileModel(
    val email: String,
    val membershipDate: String,
    val name: String,
    val offer: Boolean,
    val points: String
)