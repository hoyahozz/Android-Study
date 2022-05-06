package com.example.android.hilt.contentprovider

import com.example.android.hilt.data.LogDao
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@EntryPoint
interface LogsContentProviderEntryPoint {
    fun logDao() : LogDao
}