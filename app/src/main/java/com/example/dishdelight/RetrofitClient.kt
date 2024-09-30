package com.example.dishdelight

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:5002/dishdelight-a72d1/us-central1/api/"


    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()) // Use Gson to parse JSON
        .build()

    fun getClient(): Retrofit {
        return retrofit
    }
}

