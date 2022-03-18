package com.example.samplemvvm.config

import com.example.samplemvvm.data.remote.service.StationApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Singleton
object RetrofitObject {

    private const val API_URL = "http://openapi.seoul.go.kr:8088/"
    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: StationApi by lazy {
        retrofit.create(StationApi::class.java)
    }
}