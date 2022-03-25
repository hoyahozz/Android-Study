package com.example.samplemvvm.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.samplemvvm.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getFavoriteList() : LiveData<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(userEntity: UserEntity)

    @Delete
    suspend fun deleteFavorite(userEntity: UserEntity)
}