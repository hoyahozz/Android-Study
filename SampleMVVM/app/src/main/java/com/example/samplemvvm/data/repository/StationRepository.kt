package com.example.samplemvvm.data.repository

import com.example.samplemvvm.data.model.Station

interface StationRepository {

    suspend fun getStationList(apiKey : String) : List<Station>
}