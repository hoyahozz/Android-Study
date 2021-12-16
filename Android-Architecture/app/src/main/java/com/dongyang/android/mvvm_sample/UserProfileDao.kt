package com.dongyang.android.mvvm_sample

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserProfileDao {

    @Insert
    fun insert(userProfile: UserProfile)

    @Update
    fun update(userProfile: UserProfile)

    @Delete
    fun delete(userProfile: UserProfile)

    @Query("SELECT * FROM UserProfile")
    fun getAll() : LiveData<List<UserProfile>>
}