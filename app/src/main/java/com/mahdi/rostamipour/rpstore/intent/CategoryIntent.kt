package com.mahdi.rostamipour.rpstore.intent

sealed class CategoryIntent {
    object LoadCategories : CategoryIntent()
}