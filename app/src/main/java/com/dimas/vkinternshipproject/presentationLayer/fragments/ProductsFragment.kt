package com.dimas.vkinternshipproject.presentationLayer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.dimas.vkinternshipproject.databinding.FragmentProductsBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimas.vkinternshipproject.ProductsAdapter
import com.dimas.vkinternshipproject.presentationLayer.viewmodels.ProductsViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProductsFragment : Fragment() {
    private var _binding: FragmentProductsBinding? = null
    private val binding
        get() = _binding!!

    private val viewmodel by viewModels<ProductsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.requireState().onEach(::stateHandler).launchIn(viewLifecycleOwner.lifecycleScope)
        if (savedInstanceState == null) {
            viewmodel.loadData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun stateHandler(state: ProductsViewModel.ProductsState) {
        when (state) {
            is ProductsViewModel.ProductsState.Idle -> Unit
            is ProductsViewModel.ProductsState.Loading -> {
                showLoader(true)
                showRetryButton(false)
                showMoreButton(false)
            }

            is ProductsViewModel.ProductsState.Success -> {
                showLoader(false)
                val adapter = ProductsAdapter(state.result)
                binding.productsRv.adapter = adapter
                binding.productsRv.layoutManager = LinearLayoutManager(requireContext())
                showMoreButton(true)
                binding.moreButton.setOnClickListener { viewmodel.loadData() }
            }

            is ProductsViewModel.ProductsState.Error -> {
                showLoader(false)
                showRetryButton(true)
                binding.retry.setOnClickListener { viewmodel.loadData() }
                Toast.makeText(
                    requireContext(),
                    "Runtime error: ${state.error}. Please, retry.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showLoader(status: Boolean) {
        binding.progressBar.isVisible = status
    }

    private fun showRetryButton(status: Boolean) {
        binding.retry.isVisible = status
    }

    private fun showMoreButton(status: Boolean) {
        binding.moreButton.isVisible = status
    }

    companion object {
        fun newInstance() = ProductsFragment()
    }
}