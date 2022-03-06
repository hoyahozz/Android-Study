package com.example.daggerexample

import dagger.Module
import dagger.Provides


/*
    @Module : 의존성을 제공하는 클래스에 붙임
    @Provides : 의존성을 제공하는 메소드

   1.  컴포넌트 내에서 의존성 관리 중 중복되는 타입이 하나의 컴포넌트 내에 존재해선 안된다.
   (i.e -> return string 타입의 메소드가 2개 있으면 컴파일 에러)

   2.  @Provides 메소드에서 null 반환을 기본적으로 제한한다.
   null 허용을 원하면 @Nullable 을 추가해야 한다. 의존성 주입을 받는 부분에도 @Nullable 을 추가해야 한다.

 */

@Module
class MyModule {
    @Provides
    fun provideHelloWorld() : String {
        return "Hello Jeongho"
    }

    @Provides
    fun provideInteger() : Int {
        return 5
    }


}