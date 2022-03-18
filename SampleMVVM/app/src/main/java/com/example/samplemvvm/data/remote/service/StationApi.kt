package com.example.samplemvvm.data.remote.service

import com.example.samplemvvm.data.remote.entity.Station
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface StationApi {

    @GET("{API_KEY}/json/subwayStationMaster/1/700/")
    suspend fun getList (
        @Path("API_KEY") apikey: String?
    ) : Response<Station>
    // Response -> 오류 검출 가능

//    companion object {
//        const val API_URL = "http://openapi.seoul.go.kr:8088/"
//
//        fun create() : StationApi {
//            val retrofit: Retrofit = Retrofit.Builder().baseUrl(API_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//
//            return retrofit.create(StationApi::class.java)
//        }
//    }
}