package com.dongyang.android.samplekoin

import android.app.Application
import com.dongyang.android.samplekoin.instance.SingleInstance
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

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
        // Application 이 파괴되면 컨테이너도 함께 파괴
        startKoin { // 모듈 등록
//            androidLogger()
            androidContext(this@MyApplication)
//            androidFileProperties()
            modules(singleModule, factoryModule, repositoryModule, viewModelModule)

//            modules(module {
//                single { SingleInstance }
//            })
        }
    }

}