package com.example.samplemvvm.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Station (
    @SerializedName("subwayStationMaster")
    @Expose
    var subwayStationMaster : SubwayStationMaster
)

data class SubwayStationMaster (
    @SerializedName("list_total_count")
    @Expose
    var listTotalCount: Int? = null
)