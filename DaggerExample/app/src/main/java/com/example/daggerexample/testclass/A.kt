package com.example.daggerexample.testclass

import javax.inject.Inject

open class A {
    @set:Inject
    var a : String = "aa"
}