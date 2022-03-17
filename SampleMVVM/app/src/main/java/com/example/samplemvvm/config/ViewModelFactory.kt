package com.example.samplemvvm.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.samplemvvm.data.repository.StationRepository
import com.example.samplemvvm.ui.StationViewModel

class ViewModelFactory(
    private val stationRepository: StationRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        //        return StationViewModel(stationRepository) as T

        return when (modelClass) {

            StationViewModel::class.java -> {
                modelClass.getConstructor(STATION_REPO).newInstance(stationRepository)
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
        } as T


    }

    companion object {
        private val STATION_REPO = StationRepository::class.java
    }
}