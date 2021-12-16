package com.dongyang.android.mvvm_sample

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author : Jeong Ho Kim
 * @Created : 2021-12-07
 * @Description :
 */

@Entity
data class UserProfile (
    @PrimaryKey(autoGenerate = true)
    var id : Int?,
    var name : String,
    var phone : String,
    var address : String
    )