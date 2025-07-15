package com.mahdi.rostamipour.rpstore.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahdi.rostamipour.rpstore.intent.FavoriteIntent
import com.mahdi.rostamipour.rpstore.intent.state.FavoriteState
import com.mahdi.rostamipour.rpstore.model.FavoriteModel
import com.mahdi.rostamipour.rpstore.model.repository.FavoriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(val favoriteRepository: FavoriteRepository) : ViewModel() {

    private val _favoriteProductsState = MutableStateFlow(FavoriteState())
    val favoriteProductsState : StateFlow<FavoriteState> = _favoriteProductsState

    private val _favoriteOperation = MutableStateFlow(false)
    val favoriteOperation : StateFlow<Boolean> = _favoriteOperation

    fun handleIntent(favoriteIntent: FavoriteIntent){
        when(favoriteIntent){
            is FavoriteIntent.LoadFavoriteProducts -> {
                getAllFavorite()
            }
            is FavoriteIntent.DeleteFavoriteProduct -> {
                deleteFavoriteProduct(favoriteIntent.idProduct)
            }
            is FavoriteIntent.AddFavoriteProduct -> {
                insertFavoriteProduct(favoriteIntent.favoriteModel)
            }

            is FavoriteIntent.IsProductFavorite -> {
                //isProductFavorite(favoriteIntent.)
            }
            is FavoriteIntent.CheckFavoriteStatus -> {
                checkIfFavorite(favoriteIntent.productId)
            }
        }
    }

    private fun getAllFavorite(){
        viewModelScope.launch {
            _favoriteProductsState.value = _favoriteProductsState.value.copy(isLoading = true)
            try {
                favoriteRepository.getProducts().collect { listFavorite ->
                    _favoriteProductsState.value = _favoriteProductsState.value.copy(isLoading = false , listFavorite)
                }
            }catch (e : Exception){
                _favoriteProductsState.value = _favoriteProductsState.value.copy(error = e.message?: "Error get all favorite products")
            }
        }
    }

    private fun deleteFavoriteProduct(productId: Int){
        viewModelScope.launch {
            favoriteRepository.deleteProduct(productId)
        }
    }

    private fun insertFavoriteProduct(favoriteModel: FavoriteModel){
        viewModelScope.launch {
            try {
                favoriteRepository.addProduct(favoriteModel)
                _favoriteOperation.value = true
            }catch (e : Exception){
                _favoriteProductsState.value = _favoriteProductsState.value.copy(error = e.message ?: "error insert favorite product")
                _favoriteOperation.value = false
            }
        }
    }

    /*private fun isProductFavorite(id : Int){
        viewModelScope.launch {
            try {
                _favoriteOperation.value = favoriteRepository.isProductFavorite(id)
            }catch (e : Exception){
                _favoriteOperation.value =false
            }
        }
    }*/


    private fun checkIfFavorite(productId: Int) {
        viewModelScope.launch {
            favoriteRepository.isProductFavorite(productId).collect{
                val isFav = it
                val updatedMap = _favoriteProductsState.value.favoriteStatus.toMutableMap()
                updatedMap[productId] = isFav
                _favoriteProductsState.value = _favoriteProductsState.value.copy(favoriteStatus = updatedMap)
            }

        }
    }



}