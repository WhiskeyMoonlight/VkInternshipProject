package com.dimas.vkinternshipproject.presentationLayer.activities

import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimas.vkinternshipproject.utilities.ProductsAdapter
import com.dimas.vkinternshipproject.databinding.SearchActivityBinding
import com.dimas.vkinternshipproject.presentationLayer.viewmodels.SearchViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: SearchActivityBinding
    private val viewmodel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SearchActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewmodel.requireState().onEach(::stateHandler).launchIn(this.lifecycleScope)

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(s: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(s: String?): Boolean {
                val input = binding.search.query.toString()
                viewmodel.search(input)
                return false
            }
        })
        binding.backButton.setOnClickListener { this.finish() }
    }

    private fun stateHandler(state: SearchViewModel.SearchState) {
        when (state) {
            is SearchViewModel.SearchState.Idle -> Unit
            is SearchViewModel.SearchState.Loading -> {
                showLoader(true)
                showNothingFound(false)
                showErrorMessage(false)
            }

            is SearchViewModel.SearchState.Success -> {
                showLoader(false)
                val adapter = ProductsAdapter(state.result)
                binding.searchRv.adapter = adapter
                binding.searchRv.layoutManager = LinearLayoutManager(this)
                if (state.result.isEmpty()) {
                    showNothingFound(true)
                }
            }

            is SearchViewModel.SearchState.Error -> {
                showLoader(false)
                showErrorMessage(true)
                Toast.makeText(
                    this,
                    "Runtime error: ${state.error}. Please, retry.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showLoader(status: Boolean) {
        binding.progressBar.isVisible = status
    }

    private fun showNothingFound(status: Boolean) {
        binding.nothingFound.isVisible = status
    }

    private fun showErrorMessage(status: Boolean) {
        binding.errorMessage.isVisible = status
    }
}
