package com.dongyang.android.mvvm_sample

data class User(
    var nickname: String?,
    var name: String?,
    var gender: Int?
) {
    companion object {
        const val GENDER_MALE: Int = 0
        const val GENDER_FEMALE: Int = 1
    }
}







