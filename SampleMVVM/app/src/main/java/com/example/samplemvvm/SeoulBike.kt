package com.dongyang.android.aob.Map.Model.Bike

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SeoulBike {
    @SerializedName("rentBikeStatus")
    @Expose
    var rentBikeStatus: RentBikeStatus? = null
}