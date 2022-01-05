package com.example.recyclerviewpractice.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerviewpractice.model.User
import com.example.recyclerviewpractice.UserDataBase
import com.example.recyclerviewpractice.headerpractice.UserSealed
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    val dao = UserDataBase.INSTANCE!!.userDao()

    val userList : LiveData<List<User>> = dao.getAll()

    private val userLiveData = MutableLiveData<List<UserSealed>>() // 변화가능한 라이브데이터
    val users: LiveData<List<UserSealed>> get() = userLiveData // getter


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


    fun fetchTasks(user : List<User>) {
        viewModelScope.launch {
            val listItems = user.toListItems()
            userLiveData.postValue(listItems)
        }
    }

    private fun List<User>.toListItems(): List<UserSealed> { // Kotlin extension
        // 부모 날짜별로 데이터를 정리
        val result = arrayListOf<UserSealed>()
        var groupParentDate = ""
        this.forEach { user ->
            // 날짜가 달라지면 그룹헤더 추가
            if (groupParentDate != user.name) {
                result.add(UserSealed.Parent(user))
            }
            result.add(UserSealed.Child(user)) // 태스크 추가
            groupParentDate = user.name // 그룹 날짜를 바로 이전 날짜로 설정
        }
        return result
    }
    
}