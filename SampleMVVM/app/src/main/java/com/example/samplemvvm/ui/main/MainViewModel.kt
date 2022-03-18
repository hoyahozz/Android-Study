package com.example.samplemvvm.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samplemvvm.data.local.entity.UserEntity
import com.example.samplemvvm.data.local.repository.UserRepository
import com.example.samplemvvm.data.remote.entity.Station
import com.example.samplemvvm.data.remote.repository.StationRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(
    private val stationRepository: StationRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _station: MutableLiveData<Response<Station>> = MutableLiveData()
    val station: LiveData<Response<Station>> = _station

    fun getList(apiKey: String) {
        viewModelScope.launch {
            val response = stationRepository.getStationList(apiKey)
            _station.postValue(response)
        }
    }

    val userFavoriteList = userRepository.getList()

    fun insertFavorite(userEntity: UserEntity) {
        userRepository.insert(userEntity)
    }
}