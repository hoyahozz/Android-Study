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

package com.example.background

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.example.background.workers.BlurWorker
import com.example.background.workers.CleanupWorker
import com.example.background.workers.SaveImageToFileWorker

/* BlurActivity 를 표시하는데 필요한 데이터를 모두 저장, WorkManager 를 사용해 백그라운드 작업을 시작하는 클래스 */
class BlurViewModel(application: Application) : ViewModel() {

    internal var imageUri: Uri? = null
    internal var outputUri: Uri? = null
    internal val outputWorkInfos : LiveData<List<WorkInfo>>

    private val workManager = WorkManager.getInstance(application) // WorkManager 변수 추가
    // 싱글톤으로 구현이 되어있어 getInstance() 로 인스턴스를 받아 실행

    init {
        imageUri = getImageUri(application.applicationContext)
        outputWorkInfos = workManager.getWorkInfosByTagLiveData(TAG_OUTPUT)
    }

    /**
     * Create the WorkRequest to apply the blur and save the resulting image
     * @param blurLevel The amount to blur the image
     */
    internal fun applyBlur(blurLevel: Int) { // 적용 버튼 클릭시 메소드 호출

        val blurRequest = OneTimeWorkRequestBuilder<BlurWorker>()
            .setInputData(createInputDataForUri()) // 결과 전달
            .build()

        // 단일 워커 호출 -> enqueue
        workManager.enqueue(blurRequest)

        // workRequest : 실제 요청하게 될 개별 작업, 어떻게 작업을 처리할 것인지에 대한 정보를 담음
        // workManager.enqueue(OneTimeWorkRequest.from(BlurWorker::class.java))
        // OneTimeWorkRequest : 한 번만 실행
        // PeriodicWorkRequest : 일정 주기로 반복
        // internal : 같은 모듈 안에서만 볼 수 있음
    }

    // Worker Chain (순서대로 여러 개의 워커 실행)
    internal fun multiApplyBlur(blurLevel: Int) {
        // Worker 체인 여러 번 실행
//        var continuation = workManager
//            .beginWith(OneTimeWorkRequest.from(CleanupWorker::class.java))

        // Worker 체인 한 번에 하나씩만 실행
        var continuation = workManager
            .beginUniqueWork(IMAGE_MANIPULATION_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            OneTimeWorkRequest.from(CleanupWorker::class.java))

        for (i in 0 until blurLevel) {
            val blurBuilder = OneTimeWorkRequestBuilder<BlurWorker>()
            if(i == 0) {
                blurBuilder.setInputData(createInputDataForUri())
            }

            continuation = continuation.then(blurBuilder.build())
        }

        val save = OneTimeWorkRequestBuilder<SaveImageToFileWorker>()
            .addTag(TAG_OUTPUT) // 태그 추가
            .build()

        continuation = continuation.then(save)

        continuation.enqueue()
    }

    private fun createInputDataForUri(): Data {
        val builder = Data.Builder() // Data Builder 객체 생성
        imageUri?.let { // not null, uri면 putString 메소드 사용
            builder.putString(KEY_IMAGE_URI, imageUri.toString()) // Data 객체에 추가.
        }
        return builder.build() // Data 객체를 만들고 반환
    }

    private fun uriOrNull(uriString: String?): Uri? {
        return if (!uriString.isNullOrEmpty()) {
            Uri.parse(uriString)
        } else {
            null
        }
    }

    private fun getImageUri(context: Context): Uri {
        val resources = context.resources

        val imageUri = Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(resources.getResourcePackageName(R.drawable.android_cupcake))
            .appendPath(resources.getResourceTypeName(R.drawable.android_cupcake))
            .appendPath(resources.getResourceEntryName(R.drawable.android_cupcake))
            .build()

        return imageUri
    }

    internal fun setOutputUri(outputImageUri: String?) {
        outputUri = uriOrNull(outputImageUri)
    }

    internal fun cancelWork() {
        workManager.cancelUniqueWork(IMAGE_MANIPULATION_WORK_NAME)
    }


    class BlurViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(BlurViewModel::class.java)) {
                BlurViewModel(application) as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}
