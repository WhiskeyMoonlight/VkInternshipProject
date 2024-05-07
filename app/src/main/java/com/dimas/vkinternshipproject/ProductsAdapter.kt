package com.dimas.vkinternshipproject

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dimas.vkinternshipproject.databinding.ProductItemBinding
import com.dimas.vkinternshipproject.model.Product
import com.dimas.vkinternshipproject.presentationLayer.activities.SingleProductActivity

class ProductsAdapter(
    private val products: List<Product>
) : RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) = holder.bind(products[position])

    class ProductViewHolder(private val itemBinding: ProductItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: Product) {
            itemBinding.productTitle.text = item.title
            itemBinding.productDescription.text = item.description
            itemBinding.productPrice.text = item.price.toString()
            itemBinding.imageView.apply {
                Glide.with(this)
                    .load(item.imageUrl)
                    .into(this)
            }
            itemBinding.root.setOnClickListener {
                val context = itemBinding.root.context
                val intent = Intent(context, SingleProductActivity::class.java)
                intent.putExtra(SingleProductActivity.PRODUCT_ID, item.id)
                context.startActivity(intent)
            }
        }
    }
}