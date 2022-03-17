package com.example.samplemvvm.data

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {

    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(StationApi.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val _api: StationApi = retrofit.create(StationApi::class.java)
    val api get() = _api
}