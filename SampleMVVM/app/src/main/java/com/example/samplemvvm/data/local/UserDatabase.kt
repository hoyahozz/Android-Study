package com.example.samplemvvm.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.samplemvvm.data.local.dao.UserDao
import com.example.samplemvvm.data.local.entity.UserEntity
import com.example.samplemvvm.util.log

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase? {
            log("getInstance")
            if (INSTANCE == null) {
                synchronized(UserDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java, "favoriteDatabase.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return INSTANCE
//            else {
//                log("cannot create database instance")
//            }

        }
    }
}
