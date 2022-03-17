package com.example.samplemvvm.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samplemvvm.data.entity.Station
import com.example.samplemvvm.data.repository.StationRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class StationViewModel(private val stationRepository: StationRepository) : ViewModel() {

    private val _station : MutableLiveData<Response<Station>> = MutableLiveData()
    val station : LiveData<Response<Station>> = _station

    fun getList(apiKey : String) {
        viewModelScope.launch {
            val response = stationRepository.getStationList(apiKey)
            _station.postValue(response)
        }
    }



}