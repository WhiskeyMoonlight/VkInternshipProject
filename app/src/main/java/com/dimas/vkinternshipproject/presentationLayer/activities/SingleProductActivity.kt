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
import com.google.android.material.snackbar.Snackbar
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
                showRetry(false)
            }

            is SingleProductViewModel.SingleState.Success -> {
                showLoader(false)
                showRetry(false)
                val item = state.result
                binding.itemThumbnail.apply {
                    Glide.with(this)
                        .load(state.result.imageUrl)
                        .into(this)
                }
                binding.itemTitle.text = item.title
                binding.itemDescription.text = item.description

                "Brand: ${item.brand}".apply { binding.itemBrand.text = this }
                "${item.price} USD".apply { binding.itemPrice.text = this }
                "(${item.discount}% off!)".apply { binding.itemDiscount.text = this }
                "${item.rating}/5.00".apply { binding.itemRating.text = this }
                "In stock: ${item.stock}".apply { binding.itemStock.text = this }

                binding.backButton.setOnClickListener { this.finish() }

                binding.orderButton.setOnClickListener {
                    Snackbar.make(
                        binding.root,
                        "You have just ordered  ${item.title}!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            is SingleProductViewModel.SingleState.Error -> {
                showLoader(false)
                showRetry(true)
                Toast.makeText(
                    this,
                    "Runtime error: ${state.error}. Please, retry.",
                    Toast.LENGTH_SHORT
                ).show()

                binding.errorGoBack.setOnClickListener { this.finish() }
            }
        }
    }

    private fun showLoader(status: Boolean) {
        binding.singleProgressBar.isVisible = status
    }

    private fun showRetry(status: Boolean) {
        binding.errorAction.isVisible = status
        binding.errorMessage.isVisible = status
    }

    companion object {
        const val PRODUCT_ID = "id"
    }

}