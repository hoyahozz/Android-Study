package com.example.samplemvvm.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity (
    tableName = "User", indices = [Index(value = ["station_name"], unique = true)] // 유니크키 설정
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Int?,
    @ColumnInfo(name = "station_name")
    var stationName : String,
)