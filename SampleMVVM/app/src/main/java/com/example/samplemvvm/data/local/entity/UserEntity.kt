package com.example.samplemvvm.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (
    tableName = "User"
)
data class UserEntity(
    @PrimaryKey
    var stationName : String,
)