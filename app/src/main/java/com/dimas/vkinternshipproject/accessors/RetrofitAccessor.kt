package com.dimas.vkinternshipproject.accessors

import com.dimas.vkinternshipproject.utilities.IApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAccessor {
    private val retrofit = Retrofit.Builder()
        .baseUrl(IApi.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service by lazy {
        retrofit.create(IApi::class.java)
    }

    val apiService: IApi
        get() = service
}