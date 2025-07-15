package com.mahdi.rostamipour.rpstore.intent.state

import com.mahdi.rostamipour.rpstore.model.ProductsModel

sealed class ProductState {

    object Idle : ProductState()
    object Loading : ProductState()
    data class Success(val allProduct : List<ProductsModel>) : ProductState()
    data class Error(val message : String) : ProductState()

}