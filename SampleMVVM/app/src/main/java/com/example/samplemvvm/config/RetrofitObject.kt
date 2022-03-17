package com.example.samplemvvm.config

import com.example.samplemvvm.data.StationApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {

    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(StationApi.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: StationApi by lazy {
        retrofit.create(StationApi::class.java)
    }
}