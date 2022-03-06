package com.example.daggerexample.testclass

import javax.inject.Inject

class MyClass {

    @Inject lateinit var testStr : String

    var testInt : Int = 0
    var testDouble : Double = 0.5

    @Inject
    fun setInt(int : Int) {
        this.testInt = int
    }

    @Inject
    fun setDouble(double : Double) {
        this.testDouble = double
    }

    fun getDouble() : Double {
        return testDouble
    }

    fun getStr() : String {
        return testStr
    }

    fun getInt() : Int {
        return testInt
    }

}