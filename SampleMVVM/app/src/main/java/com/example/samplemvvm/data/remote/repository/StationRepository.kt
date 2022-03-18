package com.example.samplemvvm.data.remote.repository

import com.example.samplemvvm.config.RetrofitObject
import com.example.samplemvvm.data.remote.entity.Station
import retrofit2.Response

class StationRepository {

    suspend fun getStationList(apiKey : String) : Response<Station> {
        return RetrofitObject.api.getList(apiKey)
    }
}