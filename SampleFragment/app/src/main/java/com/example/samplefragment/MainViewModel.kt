package com.example.samplefragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _count = MutableLiveData<Int>()
    val count : LiveData<Int> get() = _count

    init {
        _count.value = 5
    }

    fun getUpdatedCount(plusCount: Int){
        _count.value = (_count.value)?.plus(plusCount)
    }
}