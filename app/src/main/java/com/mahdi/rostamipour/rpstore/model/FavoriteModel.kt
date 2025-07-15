package com.mahdi.rostamipour.rpstore.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
@Entity(tableName = "favoriteProducts")
data class FavoriteModel(
    val category: Int,
    val description: String,
    @PrimaryKey
    val id: Int,
    val offer: Boolean,
    val picture: String,
    val price: Int,
    val priceOffer: Int?,
    val title: String
) : Parcelable