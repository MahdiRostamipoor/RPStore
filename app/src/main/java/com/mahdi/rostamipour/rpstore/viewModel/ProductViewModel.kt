package com.mahdi.rostamipour.rpstore.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahdi.rostamipour.rpstore.model.repository.ProductRepository
import com.mahdi.rostamipour.rpstore.intent.ProductIntent
import com.mahdi.rostamipour.rpstore.intent.state.ProductState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(val productRepository: ProductRepository) : ViewModel() {

    private val _allProduct = MutableStateFlow<ProductState>(ProductState.Idle)
    val allProductState : StateFlow<ProductState> = _allProduct

    fun handleAllProductIntent(intent : ProductIntent){
        when (intent){
            is ProductIntent.GetProducts -> {
                getAllProducts()
            }
        }
    }

    private fun getAllProducts(){
        viewModelScope.launch {
            _allProduct.value = ProductState.Loading
            try {
                val allProducts = productRepository.getProducts()
                _allProduct.value = ProductState.Success(allProducts)
            }catch (e : Exception){
                _allProduct.value = ProductState.Error(e.message ?: "Error loading all products")
            }
        }
    }

}