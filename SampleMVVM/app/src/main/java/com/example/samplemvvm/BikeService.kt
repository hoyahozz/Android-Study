package com.dongyang.android.aob.Map.Service

import com.dongyang.android.aob.Map.Model.Bike.SeoulBike
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BikeService {
    @GET("{apikey}/json/bikeList/{start}/{end}/")
    fun getBike(
        @Path("apikey") apikey: String?,
        @Path("start") start: Int,
        @Path("end") end: Int
    ): Call<SeoulBike?>?

    companion object {
        const val SEOUL_URL = "http://openapi.seoul.go.kr:8088/"
    }
}