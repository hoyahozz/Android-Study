package com.example.background.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.background.KEY_IMAGE_URI
import com.example.background.R
import timber.log.Timber
import java.lang.IllegalArgumentException

class BlurWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    override fun doWork(): Result {

        val resourceUri = inputData.getString(KEY_IMAGE_URI) // 받은 데이터
        makeStatusNotification("Blurring Image", applicationContext)

        return try {

//            val picture = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.test)

            if(TextUtils.isEmpty(resourceUri)) {
                Timber.e(("Invalid input Uri"))
                throw IllegalArgumentException("Invalid input uri")
            }

            val resolver = applicationContext.contentResolver

            val picture = BitmapFactory.decodeStream(resolver.openInputStream(Uri.parse(resourceUri)))

            val output = blurBitmap(picture, applicationContext) // 블러 처리 된 비트맵 호출
            val outputUri = writeBitmapToFile(applicationContext, output) // 비트맵을 임시 파일로
            val outputData = workDataOf(KEY_IMAGE_URI to outputUri.toString())
//            makeStatusNotification("Output is $outputUri", applicationContext) // URI 표시 알람
            Result.success(outputData) // WorkManager 에 반환
        } catch (throwable : Throwable) {
            Timber.e(throwable, "Error applying blur")
            Result.failure()
        }
    }
}