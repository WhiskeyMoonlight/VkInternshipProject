package com.dimas.vkinternshipproject.presentationLayer.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimas.vkinternshipproject.utilities.ProductsAdapter
import com.dimas.vkinternshipproject.databinding.CategoriesActivityBinding
import com.dimas.vkinternshipproject.presentationLayer.viewmodels.CategoriesViewModel
import com.dimas.vkinternshipproject.providers.CategoriesProvider
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CategoriesActivity : AppCompatActivity() {
    private val viewModel by viewModels<CategoriesViewModel>()
    private lateinit var binding: CategoriesActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CategoriesActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.requireState().onEach(::stateHandler).launchIn(this.lifecycleScope)
        val categoriesData = CategoriesProvider.categoriesList
        val spinnerAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, categoriesData)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = spinnerAdapter
        binding.findButton.setOnClickListener {
            val category = "${binding.spinner.selectedItem}"
            viewModel.loadData(category)
        }
        binding.backButton.setOnClickListener { this.finish() }
    }

    private fun stateHandler(state: CategoriesViewModel.CategoryState) {
        when (state) {
            is CategoriesViewModel.CategoryState.Idle -> Unit
            is CategoriesViewModel.CategoryState.Loading -> {
                showLoader(true)
                showMessage(false)
            }

            is CategoriesViewModel.CategoryState.Success -> {
                showLoader(false)
                val adapter = ProductsAdapter(state.result)
                binding.categoryRv.adapter = adapter
                binding.categoryRv.layoutManager = LinearLayoutManager(this)
            }

            is CategoriesViewModel.CategoryState.Error -> {
                showLoader(false)
                showMessage(true)
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

    private fun showMessage(status: Boolean) {
        binding.errorMessage.isVisible = status
    }

}