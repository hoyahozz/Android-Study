package com.example.samplemvvm.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.samplemvvm.data.local.repository.UserRepository
import com.example.samplemvvm.data.remote.repository.StationRepository
import com.example.samplemvvm.ui.main.MainViewModel
import com.example.samplemvvm.ui.user.UserViewModel

class ViewModelFactory(
    private val stationRepository: StationRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        // return StationViewModel(stationRepository) as T

        return when (modelClass) {

            MainViewModel::class.java -> {
                modelClass.getConstructor(STATION_REPO, USER_REPO)
                    .newInstance(stationRepository, userRepository)
            }

            UserViewModel::class.java -> {
                modelClass.getConstructor(USER_REPO).newInstance(userRepository)
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
        } as T

    }

    companion object {
        private val STATION_REPO = StationRepository::class.java
        private val USER_REPO = UserRepository::class.java
    }
}