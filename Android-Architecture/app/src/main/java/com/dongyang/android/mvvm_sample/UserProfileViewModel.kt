package com.dongyang.android.mvvm_sample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserProfileViewModel : ViewModel() { // 뷰모델 클래스

    val dao : UserProfileDao
    val userList : LiveData<List<UserProfile>>


    init {
        dao = UserProfileDatabase.INSTANCE!!.userProfileDao()
        userList = dao.getAll()
    }

    fun insertUser(userProfile: UserProfile) {
        viewModelScope.launch {
            dao.insert(userProfile)
        }
    }

}