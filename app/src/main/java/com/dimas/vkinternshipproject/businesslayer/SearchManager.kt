package com.dimas.vkinternshipproject.businesslayer

import com.dimas.vkinternshipproject.providers.ProductsProvider

object SearchManager {
    suspend fun search(query: String) = ProductsProvider.search(query)
}