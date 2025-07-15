package com.mahdi.rostamipour.rpstore.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahdi.rostamipour.rpstore.intent.FilterIntent
import com.mahdi.rostamipour.rpstore.intent.state.FilterState
import com.mahdi.rostamipour.rpstore.intent.state.FilteringItemsState
import com.mahdi.rostamipour.rpstore.model.repository.FilterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FilterViewModel(val filterRepository: FilterRepository) : ViewModel() {

    private val _getProductByCategory = MutableStateFlow<FilterState>(FilterState.Idle)
    val getProductByCategoryState : StateFlow<FilterState> = _getProductByCategory

    fun handleFilter(filterIntent: FilterIntent , categoryId : Int){
        when(filterIntent){
            is FilterIntent.GetProductsByCategory -> {
                getProductByCategory(categoryId)
            }

            is FilterIntent.GetFilteringItems -> {
                getFilteringItems()
            }
        }
    }

    private fun getProductByCategory(categoryId: Int){
        viewModelScope.launch {
            _getProductByCategory.value = FilterState.Loading
            try {
                val product = filterRepository.getProductByCategory(categoryId)
                _getProductByCategory.value = FilterState.Success(product)
            }catch (e : Exception){
                _getProductByCategory.value = FilterState.Error(e.message ?: "error getByCategory")
            }
        }
    }


    private val _getFilteringItems =MutableStateFlow<FilteringItemsState>(FilteringItemsState.Idle)
    val getFilteringItemsState : StateFlow<FilteringItemsState> = _getFilteringItems

    private fun getFilteringItems(){
        viewModelScope.launch {
            _getFilteringItems.value = FilteringItemsState.Loading
            try {
                val filteringItems = filterRepository.getFilteringItems()
                _getFilteringItems.value = FilteringItemsState.Success(filteringItems)
            }catch (e : Exception){
                _getFilteringItems.value = FilteringItemsState.Error(e.message ?: "Error get filtering items")
            }
        }
    }

}