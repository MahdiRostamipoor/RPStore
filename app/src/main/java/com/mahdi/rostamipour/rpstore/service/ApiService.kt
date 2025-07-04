package com.mahdi.rostamipour.rpstore.service

import com.mahdi.rostamipour.rpstore.model.CategoryModel
import com.mahdi.rostamipour.rpstore.model.FilteringModel
import com.mahdi.rostamipour.rpstore.model.ProductsModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ApiService {

    val httpClient = HttpClient{
        install(ContentNegotiation){
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun getCategories(): List<CategoryModel> {
        return httpClient.get("http://10.0.85.2:3000/categories").body()
    }

    suspend fun getProducts(): List<ProductsModel>{
        return httpClient.get("http://10.0.85.2:3000/products").body()
    }

    suspend fun getProductsByCategory(categoryId: Int): List<ProductsModel> {
        return httpClient.get("http://10.0.85.2:3000/products-by-category"){
            url{
                parameters.append("categoryId", categoryId.toString())
            }
        }.body()
    }

    suspend fun getFilteringItems() : FilteringModel{
        return httpClient.get("http://10.0.85.2:3000/filtering").body()
    }

}