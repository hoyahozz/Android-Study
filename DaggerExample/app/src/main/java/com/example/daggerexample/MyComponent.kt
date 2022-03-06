package com.example.daggerexample

import androidx.annotation.NonNull
import com.example.daggerexample.testclass.B
import com.example.daggerexample.testclass.MyClass
import com.example.daggerexample.testclass.MySubClass
import dagger.Component
import dagger.MembersInjector

/*
    컴포넌트
    바인딩된 모듈로부터 오브젝트 그래프를 생성하는 핵심적인 역할을 수행한다.
    interface 혹은 abstract 클래스에만 붙일 수 있다.
    이로 인해 생성된 클래스는 접두어 Dagger 와 @Component 가 붙은 클래스 이름이 합쳐진 형식의 이름을 갖는다.
    -> (DaggerMyComponent)

    1. 생성 시 최소한 하나의 추상적인 메소드를 가져야 한다.

    제공할 의존성들을 메소드로 제공해야 함
    @Component 에 참조된 모듈 클래스로부터 의존성을 제공받는다.

 */

@Component(modules = [MyModule::class, MySubModule::class])
interface MyComponent {
    @NonNull
    fun getString(): String // 프로비전 메소드, 바인드된 모듈로부터 의존성을 제공한다. (매개 변수를 갖지 않으면서 반환형은 모듈로부터 제공되는 메소드)

    fun getInteger(): Int

    fun getDouble(): Double

    fun getSomeType(any: Any): Any // 하나의 매개변수를 갖는 멤버-인젝션 메소드, 매개변수 타입을 반환형으로 갖는 메소드.

    fun inject(myClass: MySubClass)

    fun childInjectTest(b: B)

    fun getInjector(): MembersInjector<MyClass> // 매개 변수가 없을 때

    // 반드시 매개 변수를 가지지 않고 컴포넌트 타입 혹은 컴포넌트의 슈퍼 타입을 반환하는 추상 메소드를 하나 포함해야 한다. (빌드 메소드)
    @Component.Builder // 컴포넌트 객체화, 컴포넌트 타입 내에 선언
    interface Builder {
        fun setMyModule(myModule: MyModule?): Builder
        fun build(): MyComponent // 빌드 메소드
    }
}