package com.example.recyclerviewpractice

import android.service.autofill.UserData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    val dao = UserDataBase.INSTANCE!!.userDao()

    val userList : LiveData<List<User>> = dao.getAll()

//    private val _UserList = MutableLiveData<User>()
//    val UserList : LiveData<User> get() = _UserList


    fun update(user: User) {
        viewModelScope.launch {
            dao.update(user)
        }
    }

    fun insert(user: User) {
        viewModelScope.launch {
            dao.insert(user)
        }
    }

    fun delete(user: User) {
        viewModelScope.launch {
            dao.delete(user)
        }
    }

}