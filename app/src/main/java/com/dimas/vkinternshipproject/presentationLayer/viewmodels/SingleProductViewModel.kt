package com.dimas.vkinternshipproject.presentationLayer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimas.vkinternshipproject.ProductsManager
import com.dimas.vkinternshipproject.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SingleProductViewModel : ViewModel() {
    private val state = MutableStateFlow<SingleState>(SingleState.Idle)
    fun requireState() = state.asStateFlow()

    fun loadProduct(id: Int) {
        state.value = SingleState.Loading
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val result = ProductsManager.getProductById(id)
                    state.value = SingleState.Success(result)
                } catch (e: Throwable) {
                    state.value = SingleState.Error(e)
                }
            }
        }
    }

    sealed class SingleState {
        data object Idle : SingleState()
        data object Loading : SingleState()
        class Success(val result: Product) : SingleState()
        class Error(val error: Throwable) : SingleState()
    }
}