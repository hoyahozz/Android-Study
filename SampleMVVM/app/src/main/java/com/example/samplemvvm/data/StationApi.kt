package com.example.samplemvvm.data

import com.example.samplemvvm.data.model.Station
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

interface StationApi {

    @GET("{API_KEY}/json/subwayStationMaster/1/5/")
    fun getList (
        @Path("API_KEY") apikey: String?
    ) : Call<Station>?

//    @GET("sample/json/subwayStationMaster/1/5/")
//    fun getList() : Call<List<Station>>?


    companion object {
        const val API_URL = "http://openapi.seoul.go.kr:8088/"

        fun create() : StationApi {
            val retrofit: Retrofit = Retrofit.Builder().baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(StationApi::class.java)
        }
    }
}