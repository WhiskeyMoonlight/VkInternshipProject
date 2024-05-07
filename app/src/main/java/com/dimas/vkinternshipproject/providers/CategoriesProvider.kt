package com.dimas.vkinternshipproject.providers

import com.dimas.vkinternshipproject.RetrofitAccessor

object CategoriesProvider {
    suspend fun getCategories(): List<String> {
        val response = RetrofitAccessor.apiService.getCategoriesList()
        return when (response.isSuccessful) {
            true -> response.body()!!
            else -> throw Throwable(response.raw().message())
        }
    }
}