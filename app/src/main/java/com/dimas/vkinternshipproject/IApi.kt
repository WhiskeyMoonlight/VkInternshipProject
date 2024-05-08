package com.dimas.vkinternshipproject

import com.dimas.vkinternshipproject.model.Product
import com.dimas.vkinternshipproject.model.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IApi {
    @GET("/products")
    suspend fun getAllProducts(): Response<ProductsResponse>

    @GET("/products/{id}")
    suspend fun findProductById(@Path(value = "id") id: Int): Response<Product>

    @GET("/products/categories")
    suspend fun getCategoriesList(): Response<List<String>>

    @GET("/products/search")
    suspend fun search(@Query("q") query: String): Response<ProductsResponse>

    @GET("/products")
    suspend fun getProductsParametrized(
        @Query("skip")
        skip: Int,

        @Query("limit")
        limit: Int
    ): Response<ProductsResponse>

    companion object {
        const val BASE_URL = "https://dummyjson.com"
    }
}