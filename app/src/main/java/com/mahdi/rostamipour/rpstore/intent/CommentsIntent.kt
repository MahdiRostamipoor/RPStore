package com.mahdi.rostamipour.rpstore.intent

sealed class CommentsIntent {
    object GetCommentsProduct : CommentsIntent()
}