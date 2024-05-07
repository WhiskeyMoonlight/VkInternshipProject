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

class ProductsViewModel : ViewModel() {
    private val state = MutableStateFlow<ProductsState>(ProductsState.Idle)
    private val limit = 20
    private var skip = 0

    private val products: MutableList<Product> = emptyList<Product>().toMutableList()

    fun requireState() = state.asStateFlow()

    fun loadData() {
        state.value = ProductsState.Loading
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val res = ProductsManager.getProductsParametrized(skip, limit)
                    products.addAll(res.data)
                    skip += limit
                    state.value = ProductsState.Success(products)
                } catch (e: Throwable) {
                    state.value = ProductsState.Error(e)
                }
            }
        }
    }

    sealed class ProductsState {
        data object Idle : ProductsState()
        data object Loading : ProductsState()
        class Success(val result: List<Product>) : ProductsState()
        class Error(val error: Throwable) : ProductsState()
    }

}