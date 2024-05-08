package com.dimas.vkinternshipproject.providers

import com.dimas.vkinternshipproject.accessors.RetrofitAccessor
import com.dimas.vkinternshipproject.model.Product
import com.dimas.vkinternshipproject.model.ProductsResponse

object ProductsProvider {

    suspend fun getProductById(id: Int): Product {
        val response = RetrofitAccessor.apiService.findProductById(id)
        return when (response.isSuccessful) {
            true -> response.body()!!
            else -> throw Throwable(response.raw().message())
        }
    }

    suspend fun search(query: String): ProductsResponse {
        val response = RetrofitAccessor.apiService.search(query)
        return when (response.isSuccessful) {
            true -> response.body()!!
            else -> throw Throwable(response.raw().message())
        }
    }

    suspend fun getProductsParametrized(skip: Int, limit: Int): ProductsResponse {
        val response = RetrofitAccessor
            .apiService
            .getProductsParametrized(skip, limit)
        return when (response.isSuccessful) {
            true -> response.body()!!
            else -> throw Throwable(response.raw().message())
        }
    }

    suspend fun getProductsByCategory(category: String): ProductsResponse {
        val response = RetrofitAccessor
            .apiService
            .getProductsByCategory(category)
        return when (response.isSuccessful) {
            true -> response.body()!!
            else -> throw Throwable(response.raw().message())
        }
    }
}