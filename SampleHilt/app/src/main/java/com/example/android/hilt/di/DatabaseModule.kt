package com.example.android.hilt.di

import android.content.Context
import androidx.room.Room
import com.example.android.hilt.data.AppDatabase
import com.example.android.hilt.data.LogDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    // 항상 동일한 데이터베이스 인스턴스 제공을 위해 @Singleton 주석을 추가
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext : Context) : AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "logging.db"
        ).build()
    }

    // Provides 로 인스턴스 제공
    // LogDao 인스턴스를 제공할 때 database.logDao() 가 실행되어야 한다고 알림
    @Provides
    fun provideLogDao(database : AppDatabase) : LogDao {
        return database.logDao()
    }
}