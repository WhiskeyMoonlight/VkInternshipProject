package com.dimas.vkinternshipproject.providers

import com.dimas.vkinternshipproject.RetrofitAccessor
import com.dimas.vkinternshipproject.model.Product
import com.dimas.vkinternshipproject.model.ProductsResponse

object ProductsProvider {
    suspend fun getProducts(): ProductsResponse {
        val response = RetrofitAccessor.apiService.getAllProducts()
        return when (response.isSuccessful) {
            true -> response.body()!!
            else -> throw Throwable(response.raw().message())
        }
    }

    suspend fun getProductById(id: Int): Product {
        val response = RetrofitAccessor.apiService.findProductById(id)
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
}