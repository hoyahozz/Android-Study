package com.dongyang.android.mvvm_sample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserProfileViewModel : ViewModel() { // 뷰모델 클래스
    var livedata = MutableLiveData<UserProfile>()
}