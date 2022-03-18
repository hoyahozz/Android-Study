package com.example.samplemvvm.data.remote.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Station(
    @SerializedName("subwayStationMaster")
    @Expose
    var subwayStationMaster: SubwayStationMaster
)

data class SubwayStationMaster(
    @SerializedName("list_total_count")
    @Expose
    var listTotalCount: Int? = null,
    var row: List<Row>
)

data class Row(
    @SerializedName("STATN_ID")
    @Expose
    var station_id: String,
    @SerializedName("STATN_NM")
    @Expose
    var station_name: String
)