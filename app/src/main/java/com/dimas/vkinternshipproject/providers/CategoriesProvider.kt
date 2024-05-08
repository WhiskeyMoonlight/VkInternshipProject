package com.dimas.vkinternshipproject.providers

import com.dimas.vkinternshipproject.accessors.RetrofitAccessor

object CategoriesProvider {

    private var _categoriesList: MutableList<String> = mutableListOf()
    val categoriesList: List<String>
        get() = _categoriesList.toList()


    suspend fun getCategories() {
        val response = RetrofitAccessor.apiService.getCategoriesList()
        return when (response.isSuccessful) {
            true -> _categoriesList = response.body()!!.toMutableList()
            else -> throw Throwable(response.raw().message())
        }
    }
}