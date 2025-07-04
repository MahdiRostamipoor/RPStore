package com.mahdi.rostamipour.rpstore.intent.state

import com.mahdi.rostamipour.rpstore.model.FilteringModel

sealed class FilteringItemsState {

    object Idle : FilteringItemsState()
    object Loading : FilteringItemsState()
    data class Success(val filterItems : FilteringModel) : FilteringItemsState()
    data class Error(val message : String) : FilteringItemsState()

}