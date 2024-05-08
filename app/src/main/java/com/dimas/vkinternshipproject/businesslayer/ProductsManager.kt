package com.dimas.vkinternshipproject.businesslayer

import com.dimas.vkinternshipproject.providers.CategoriesProvider
import com.dimas.vkinternshipproject.providers.ProductsProvider

object ProductsManager {


    suspend fun getProductById(id: Int) = ProductsProvider.getProductById(id)

    suspend fun getCategories() = CategoriesProvider.getCategories()

    suspend fun getProductsByCategory(cat: String) =
        ProductsProvider.getProductsByCategory(cat)

    suspend fun getProductsParametrized(skip: Int, limit: Int) =
        ProductsProvider.getProductsParametrized(skip, limit)
}