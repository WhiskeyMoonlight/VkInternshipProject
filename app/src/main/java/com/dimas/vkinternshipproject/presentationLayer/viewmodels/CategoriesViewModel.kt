package com.dimas.vkinternshipproject.presentationLayer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimas.vkinternshipproject.businesslayer.ProductsManager
import com.dimas.vkinternshipproject.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoriesViewModel : ViewModel() {
    private val state = MutableStateFlow<CategoryState>(CategoryState.Idle)
    fun requireState() = state.asStateFlow()

    fun loadData(category: String) {
        state.value = CategoryState.Loading
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val result = ProductsManager.getProductsByCategory(category)
                    state.value = CategoryState.Success(result.data)
                } catch (e: Throwable) {
                    state.value = CategoryState.Error(e)
                }
            }
        }
    }

    sealed class CategoryState {
        data object Idle : CategoryState()
        data object Loading : CategoryState()
        class Success(val result: List<Product>) : CategoryState()
        class Error(val error: Throwable) : CategoryState()
    }
}