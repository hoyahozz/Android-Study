package com.example.daggerexample.testclass

import javax.inject.Inject

class C : B() {
    @set:Inject
    var c : String = "cc" // 얘는 고대로 'cc'로 나옴

    fun getStrings() : String {
        return c
    }
}