package com.example.recyclerviewpractice

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey(autoGenerate = true)
    var id : Int?,
    var name : String,
    var phone : String
    )