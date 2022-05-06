/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.hilt

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.room.Room
import com.example.android.hilt.data.AppDatabase
import com.example.android.hilt.data.LoggerLocalDataSource
import com.example.android.hilt.navigator.AppNavigator
import com.example.android.hilt.navigator.AppNavigatorImpl
import com.example.android.hilt.util.DateFormatter

// 컨테이너 역할, 앱이 소멸될 때 함께 소멸된다.
// 수동으로 DI를 진행해야 하기 때문에 확장성도 떨어지고, 개발자가 실수할 가능성이 높아진다.
//class ServiceLocator(applicationContext: Context) {
//
//    private val logsDatabase = Room.databaseBuilder(
//        applicationContext,
//        AppDatabase::class.java,
//        "logging.db"
//    ).build()
//
//    val loggerLocalDataSource = LoggerLocalDataSource(logsDatabase.logDao())
//
//    fun provideDateFormatter() = DateFormatter()
//
//    fun provideNavigator(activity: FragmentActivity): AppNavigator {
//        return AppNavigatorImpl(activity)
//    }
//}
