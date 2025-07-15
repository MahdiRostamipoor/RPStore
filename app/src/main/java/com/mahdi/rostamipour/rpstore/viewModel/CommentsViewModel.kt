package com.mahdi.rostamipour.rpstore.viewModel

import androidx.compose.material3.Switch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahdi.rostamipour.rpstore.intent.CommentsIntent
import com.mahdi.rostamipour.rpstore.intent.state.GetCommentsState
import com.mahdi.rostamipour.rpstore.model.repository.CommentsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CommentsViewModel(val commentsRepository: CommentsRepository) : ViewModel() {

    private val _getCommentsState = MutableStateFlow<GetCommentsState>(GetCommentsState.Idle)
    val getCommentsState : StateFlow<GetCommentsState> = _getCommentsState

    fun handleComment(intent: CommentsIntent,productId : Int){
        when(intent){
            is CommentsIntent.GetCommentsProduct -> {
                getComments(productId)
            }
        }
    }

    private fun getComments(productId : Int){
        viewModelScope.launch {
            _getCommentsState.value = GetCommentsState.Loading
            try {
                val comments = commentsRepository.getComments(productId)
                _getCommentsState.value = GetCommentsState.Success(comments)
            }catch (e : Exception){
                _getCommentsState.value = GetCommentsState.Error(e.message ?: "Error get comments")
            }
        }
    }

}