package com.example.samplemvvm.ui.user

import com.example.samplemvvm.data.local.entity.UserEntity


interface UserClickListener {
    fun onListItemClick(item: UserEntity)
}