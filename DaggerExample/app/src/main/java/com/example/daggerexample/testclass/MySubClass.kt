package com.example.daggerexample.testclass

import javax.inject.Inject

class MySubClass() {

    lateinit var testStr : String
    var testInt : Int = 0

    @Inject
    fun setClass(str: String, int : Int) {
        this.testStr = str
        this.testInt = int
    }

    fun getStr() : String {
        return testStr
    }

    fun getInt() : Int {
        return testInt
    }

}