package com.mahdi.rostamipour.rpstore.intent.state

import com.mahdi.rostamipour.rpstore.model.CommentsModel

sealed class GetCommentsState {
    object Idle : GetCommentsState()
    object Loading : GetCommentsState()
    data class Success(val comments : List<CommentsModel>) : GetCommentsState()
    data class Error(val error : String) : GetCommentsState()
}