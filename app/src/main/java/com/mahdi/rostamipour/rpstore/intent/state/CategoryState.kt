package com.mahdi.rostamipour.rpstore.intent.state

import com.mahdi.rostamipour.rpstore.model.CategoryModel

sealed class CategoryState {

    object Idle : CategoryState()
    object Loading : CategoryState()
    data class Success(val categories: List<CategoryModel>) : CategoryState()
    data class Error(val message: String) : CategoryState()

}