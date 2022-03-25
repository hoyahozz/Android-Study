package com.example.samplemvvm.data.local.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.samplemvvm.MyApplication
import com.example.samplemvvm.data.local.UserDatabase
import com.example.samplemvvm.data.local.entity.UserEntity
import com.example.samplemvvm.util.log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepository(application: Application) {

    private val userDatabase = UserDatabase.getInstance(application)!!
    private val userDao = userDatabase.userDao()

    private val list : LiveData<List<UserEntity>> = userDao.getFavoriteList()

    companion object{
        private var sInstance: UserRepository? = null
        fun getInstance(): UserRepository {
            return sInstance
                ?: synchronized(this){
                    val instance = UserRepository(MyApplication.instance)
                    sInstance = instance
                    instance
                }
        }
    }

    fun getList() : LiveData<List<UserEntity>> {
        return this.list
    }

    fun insert(userEntity: UserEntity) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                userDao.insertFavorite(userEntity)
            }
        } catch (e : Exception) {
            log(e.toString())
        }
    }

    fun delete(userEntity: UserEntity) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                userDao.deleteFavorite(userEntity)
            }
        } catch (e : Exception) {
            log(e.toString())
        }
    }
}