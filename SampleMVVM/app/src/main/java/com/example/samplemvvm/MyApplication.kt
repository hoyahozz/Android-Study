package com.example.samplemvvm

import android.app.Application
import android.content.Context
import com.example.samplemvvm.data.local.repository.UserRepository
import com.example.samplemvvm.util.log

/**
 * 앱 실행 시 가장 먼저 진입
 */
class MyApplication : Application() {
    init {
        instance = this
    }

    companion object {
        lateinit var instance: MyApplication
        fun applicationContext(): Context {
            return instance.applicationContext
        }
        fun getRepository(): UserRepository{
            return UserRepository.getInstance()
        }
    }

    override fun onCreate() {
        log("application create")
        super.onCreate()

//        startKoin {
//            androidContext(this@MyApplication)
//            androidFileProperties()
//            modules(listOf(repositoryModule))
//        }
    }
}