package com.mahdi.rostamipour.rpstore.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahdi.rostamipour.rpstore.model.repository.CategoryRepository
import com.mahdi.rostamipour.rpstore.intent.CategoryIntent
import com.mahdi.rostamipour.rpstore.intent.state.CategoryState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(val categoryRepository: CategoryRepository) : ViewModel(){
    private val _categoryState = MutableStateFlow<CategoryState>(CategoryState.Idle)
    val categoryState : StateFlow<CategoryState> = _categoryState

    fun handleIntent(intent: CategoryIntent) {
        when (intent) {
            is CategoryIntent.LoadCategories -> {
                loadCategories()
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _categoryState.value = CategoryState.Loading
            try {
                val categories = categoryRepository.getCategories()
                _categoryState.value = CategoryState.Success(categories)
            } catch (e: Exception) {
                _categoryState.value = CategoryState.Error(e.message ?: "Error loading categories")
            }
        }
    }
}