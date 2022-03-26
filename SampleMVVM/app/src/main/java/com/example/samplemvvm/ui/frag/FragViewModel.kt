package com.example.samplemvvm.ui.frag

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FragViewModel : ViewModel() {

    var count = MutableLiveData<Int>()

    init {
        count.value = 5
    }

    fun getUpdatedCount(plusCount: Int){
        count.value = (count.value)?.plus(plusCount)
    }
}