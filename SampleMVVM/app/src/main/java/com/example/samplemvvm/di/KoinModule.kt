package com.example.samplemvvm.di

import androidx.lifecycle.ViewModelProvider
import com.example.samplemvvm.MyApplication
import com.example.samplemvvm.config.ViewModelFactory
import com.example.samplemvvm.data.local.repository.UserRepository
import com.example.samplemvvm.data.remote.repository.StationRepository
import org.koin.dsl.module

val viewModelFactoryModule = module {
    single<ViewModelProvider.Factory> { ViewModelFactory(get(), get()) }
}

val repositoryModule = module {
    single {
        UserRepository(MyApplication.instance)
    }
    single {
        StationRepository()
    }
}