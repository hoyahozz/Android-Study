package com.example.daggerexample

import com.example.daggerexample.testclass.*


fun main() {
    val myClass = MyClass()
    val mySubClass = MySubClass()

    val mComponent = DaggerMyComponent.create()

//    mComponent.inject(mySubClass)
//
//    val str = myClass.getStr()
//    val int = myClass.getInt()
//    val double = mComponent.getDouble()
//    println("조회 결과 :: $str $int $double")
//

//    val injector = mComponent.getInjector()
//    injector.injectMembers(myClass)
//
//    val str2 = myClass.getStr()
//    val int2 = myClass.getInt()
//    println("조회 결과 :: $str2 $int2")

    val a = A()
    val b = B()
    val c = C()

    mComponent.childInjectTest(b)

    println(a.a)
    println("${b.a} + ${b.b}")
    println("${c.a} + ${c.b} + ${c.c}")
}