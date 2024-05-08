package com.dimas.vkinternshipproject

import com.dimas.vkinternshipproject.providers.CategoriesProvider
import com.dimas.vkinternshipproject.providers.ProductsProvider

object ProductsManager {
    suspend fun getProducts() = ProductsProvider.getProducts()

    suspend fun getProductById(id: Int) = ProductsProvider.getProductById(id)

    suspend fun getCategories() = CategoriesProvider.getCategories()

    suspend fun getProductsParametrized(skip: Int, limit: Int) =
        ProductsProvider.getProductsParametrized(skip, limit)

    suspend fun search(query: String) = ProductsProvider.search(query)
}