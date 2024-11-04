package com.codecrafters.dishdelight

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://dish-delight-api-v1.vercel.app/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()) // Use Gson to parse JSON
        .build()

    fun getClient(): Retrofit {
        return retrofit
    }
}

