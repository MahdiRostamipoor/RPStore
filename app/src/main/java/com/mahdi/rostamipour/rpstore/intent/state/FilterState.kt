package com.mahdi.rostamipour.rpstore.intent.state

import com.mahdi.rostamipour.rpstore.model.ProductsModel

sealed class FilterState {

    object Idle : FilterState()
    object Loading : FilterState()
    data class Success(val products: List<ProductsModel>) : FilterState()
    data class Error(val message: String) : FilterState()

}