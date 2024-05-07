package com.dimas.vkinternshipproject.presentationLayer.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dimas.vkinternshipproject.databinding.ActivitySingleProductBinding
import com.dimas.vkinternshipproject.presentationLayer.viewmodels.SingleProductViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SingleProductActivity : AppCompatActivity() {
    private val viewModel by viewModels<SingleProductViewModel>()
    private lateinit var binding: ActivitySingleProductBinding
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.requireState().onEach(::stateHandler).launchIn(this.lifecycleScope)
        id = intent.getIntExtra(PRODUCT_ID, 0)
        if (savedInstanceState == null) {
            viewModel.loadProduct(id)
        }
    }

    private fun stateHandler(state: SingleProductViewModel.SingleState) {
        when (state) {
            is SingleProductViewModel.SingleState.Idle -> Unit
            is SingleProductViewModel.SingleState.Loading -> {
                showLoader(true)
            }

            is SingleProductViewModel.SingleState.Success -> {
                showLoader(false)
                val item = state.result
                binding.itemThumbnail.apply {
                    Glide.with(this)
                        .load(state.result.imageUrl)
                        .into(this)
                }
                binding.itemTitle.text = item.title
                binding.itemDescription.text = item.description
                binding.itemBrand.text = item.brand
                binding.itemPrice.text = item.price.toString()
                binding.itemDiscount.text = item.discount.toString()
                binding.itemRating.text = item.rating.toString()
                binding.itemStock.text = item.stock.toString()
            }

            is SingleProductViewModel.SingleState.Error -> {
                showLoader(false)
                Toast.makeText(
                    this,
                    "Runtime error: ${state.error}. Please, retry.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showLoader(status: Boolean) {
        binding.singleProgressBar.isVisible = status
    }


    companion object {
        const val PRODUCT_ID = "id"
    }


}