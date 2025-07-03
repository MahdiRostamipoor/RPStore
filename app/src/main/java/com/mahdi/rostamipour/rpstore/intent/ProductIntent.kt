package com.mahdi.rostamipour.rpstore.intent

sealed class ProductIntent {
    object GetProducts : ProductIntent()
}