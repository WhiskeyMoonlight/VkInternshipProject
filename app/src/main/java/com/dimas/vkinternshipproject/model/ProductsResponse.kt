package com.dimas.vkinternshipproject.model

import com.google.gson.annotations.SerializedName

data class ProductsResponse(
    @SerializedName("products")
    val data: List<Product>
)

data class Product(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("price")
    val price: Int,

    @SerializedName("discountPercentage")
    val discount: Double,

    @SerializedName("rating")
    val rating: Double,

    @SerializedName("stock")
    val stock: Int,

    @SerializedName("brand")
    val brand: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("thumbnail")
    val imageUrl: String,

    @SerializedName("images")
    val imagesList: List<String>
)
