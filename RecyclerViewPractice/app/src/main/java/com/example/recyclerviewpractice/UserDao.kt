package com.example.recyclerviewpractice

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.recyclerviewpractice.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM User ORDER BY id DESC")
    fun getAll() : LiveData<List<User>>

}