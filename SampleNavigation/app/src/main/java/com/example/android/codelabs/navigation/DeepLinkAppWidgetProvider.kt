/*
 * Copyright (C) 2018 The Android Open Source Project
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

package com.example.android.codelabs.navigation

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.Bundle
import android.widget.RemoteViews
import androidx.navigation.NavDeepLinkBuilder

/**
 * App Widget that deep links you to the [DeepLinkFragment].
 */

// 딥 링크 지원도 포함
// 네비게이션 XML에서 app:startDestination 으로 지정된 대상으로 백스택이 적용됨.
// 즉 요기서는 딥링크 프래그먼트 -> 홈프래그먼트로 이동되는 루트

class DeepLinkAppWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        val remoteViews = RemoteViews(
            context.packageName,
            R.layout.deep_link_appwidget
        )

        val args = Bundle()
        args.putString("myarg", "From Widget")
        // TODO STEP 10 - construct and set a PendingIntent using DeepLinkBuilder
        val pendingIntent = NavDeepLinkBuilder(context)
                .setGraph(R.navigation.mobile_navigation) // 네비에기션 그래프 설정
                .setDestination(R.id.deeplink_dest) // 정크 연결위치 지정
                .setArguments(args) // 아규먼트 설정
                .createPendingIntent()

        remoteViews.setOnClickPendingIntent(R.id.deep_link_button, pendingIntent)
        // TODO END STEP 10
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews)
    }
}
