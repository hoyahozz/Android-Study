package com.dongyang.android.mvvm_sample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    var userLiveData = MutableLiveData<User>()

    fun editData(user : User) {
        userLiveData.postValue(user)
    }
}