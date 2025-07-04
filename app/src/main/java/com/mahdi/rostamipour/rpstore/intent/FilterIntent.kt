package com.mahdi.rostamipour.rpstore.intent

sealed class FilterIntent {
    object GetProductsByCategory : FilterIntent()
    object GetFilteringItems : FilterIntent()
}