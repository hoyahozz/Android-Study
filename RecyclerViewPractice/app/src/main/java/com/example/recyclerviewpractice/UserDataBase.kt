package com.example.recyclerviewpractice

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.recyclerviewpractice.model.User

@Database(entities = arrayOf(User::class), version = 1)
abstract class UserDataBase : RoomDatabase() {
    abstract fun userDao() : UserDao

    companion object {
        var INSTANCE: UserDataBase? = null

        fun getInstance(context: Context): UserDataBase? {
            if (INSTANCE == null) {
                synchronized(UserDataBase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserDataBase::class.java, "list.db"
                    )
                        .addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                            }
                        })
                        .fallbackToDestructiveMigration() // 업데이트시 모든 데이터를 드랍한다.
                        .build()
                }
            }
            return INSTANCE
        }
    }
}