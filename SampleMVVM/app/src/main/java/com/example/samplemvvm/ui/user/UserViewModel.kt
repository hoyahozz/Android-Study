package com.example.samplemvvm.ui.user

import androidx.lifecycle.ViewModel
import com.example.samplemvvm.data.local.entity.UserEntity
import com.example.samplemvvm.data.local.repository.UserRepository

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    val userFavoriteList = userRepository.getList()

    fun deleteFavorite(userEntity: UserEntity) {
        userRepository.delete(userEntity)
    }
}