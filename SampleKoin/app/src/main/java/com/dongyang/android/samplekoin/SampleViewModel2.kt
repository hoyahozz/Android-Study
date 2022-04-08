package com.dongyang.android.samplekoin

import androidx.lifecycle.ViewModel

class SampleViewModel2(
    private val repository: SampleRepository,
    private val repository2: SampleRepository2
) : ViewModel() {

    fun hello() = repository.hello()
    fun hello2() = repository2.hello()
}