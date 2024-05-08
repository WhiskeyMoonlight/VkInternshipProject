package com.dimas.vkinternshipproject.presentationLayer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dimas.vkinternshipproject.businesslayer.SearchManager
import com.dimas.vkinternshipproject.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel: ViewModel() {
    private val state = MutableStateFlow<SearchState>(SearchState.Idle)

    fun requireState() = state.asStateFlow()

    fun search(query: String) {
        state.value = SearchState.Loading
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val result = SearchManager.search(query)
                    state.value = SearchState.Success(result.data)
                } catch (e: Throwable) {
                    state.value = SearchState.Error(e)
                }
            }
        }
    }

    sealed class SearchState {
        data object Idle : SearchState()
        data object Loading : SearchState()
        class Success(val result: List<Product>) : SearchState()
        class Error(val error: Throwable) : SearchState()
    }
}