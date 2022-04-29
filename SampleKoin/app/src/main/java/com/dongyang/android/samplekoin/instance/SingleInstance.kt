package com.dongyang.android.samplekoin.instance

class SingleInstance {

    companion object {
        var count : Int = 0
    }

    init { // 객체 생성시마다 1씩 증가하며 생성된 새 객체의 수를 추적하는데 사용한다.
        ++count
    }

    fun hello() = "i am single instance number $count"

}