package com.dongyang.android.samplekoin

import androidx.lifecycle.ViewModel

class SampleViewModel(private val repository: SampleRepository) : ViewModel() {

    fun hello() = repository.hello()
}