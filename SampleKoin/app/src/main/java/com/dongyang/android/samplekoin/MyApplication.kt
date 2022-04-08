package com.dongyang.android.samplekoin

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.context.startKoin

class MyApplication : Application() {
    companion object {
        lateinit var instance : MyApplication
    }

    init {
        instance = this
    }

    override fun onCreate() {
        log("application ON")
        super.onCreate()

        // 글로벌 컨텍스트에서 주로 진행되며, 이는 싱글톤이어야 한다.
        // 글로벌 컨텍스트 = 응용 프로그램의 기본 컨텍스트
        startKoin { // 모듈 등록
            androidContext(this@MyApplication)
            androidFileProperties()
            modules(singleModule, factoryModule, repositoryModule, viewModelModule)
        }
    }

}