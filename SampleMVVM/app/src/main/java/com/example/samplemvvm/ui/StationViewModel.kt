package com.example.samplemvvm.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.samplemvvm.data.model.Station

class StationViewModel : ViewModel() {

    private val _stationList : MutableLiveData<List<Station>> = MutableLiveData()
    val stationList : LiveData<List<Station>> = _stationList



}