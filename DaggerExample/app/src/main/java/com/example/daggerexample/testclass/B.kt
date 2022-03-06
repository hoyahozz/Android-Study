package com.example.daggerexample.testclass

import javax.inject.Inject

open class B : A() {
    @set:Inject
    var b : String = "bb"

    open fun getString() : String {
        return b
    }
}