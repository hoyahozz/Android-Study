package com.dongyang.android.mvvm_sample

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = arrayOf(UserProfile::class), version = 1)
abstract class UserProfileDatabase : RoomDatabase() {
    abstract fun userProfileDao() : UserProfileDao

    companion object {
        var INSTANCE: UserProfileDatabase? = null

        fun getInstance(context: Context): UserProfileDatabase? {
            if (INSTANCE == null) {
                synchronized(UserProfileDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserProfileDatabase::class.java, "list.db"
                    )
                        .addCallback(object : RoomDatabase.Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                            }
                        })
                        .fallbackToDestructiveMigration() // 업데이트시 모든 데이터를 드랍한다.
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}