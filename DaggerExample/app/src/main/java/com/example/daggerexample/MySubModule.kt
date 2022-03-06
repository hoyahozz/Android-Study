package com.example.daggerexample

import dagger.Module
import dagger.Provides

/*
    모듈의 상속
    MyModule 의 객체도 함께 바인딩한다.
    주의할 점 -> 상속시 중복되는 타입이 존재하면 안된다.
 */

@Module(includes = [MyModule::class])
class MySubModule {
    @Provides
    fun provideDouble() : Double {
        return 0.345
    }
}