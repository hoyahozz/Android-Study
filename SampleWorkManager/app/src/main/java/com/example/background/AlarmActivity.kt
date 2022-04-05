package com.example.background

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.background.databinding.ActivityAlarmBinding
import com.example.background.workers.AlarmWorker
import java.util.concurrent.TimeUnit

class AlarmActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAlarmBinding
    private val workManager by lazy {
        WorkManager.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // WorkerManager 는 커스텀 파라미터를 지원하지 않기 때문에 setInputData() 를 통해 데이터를 주입
        val inputData = Data.Builder()
            .putString("ALARM_TEST", "Check01")
            .build()

        // 1분에 한번 알람
        binding.alarmButton.setOnClickListener {
            val oneTimeworkRequest = OneTimeWorkRequestBuilder<AlarmWorker>().build()

            val periodicWorkRequest = PeriodicWorkRequestBuilder<AlarmWorker>(16, TimeUnit.MINUTES) // 15분보다는 길게 설정해야 함
                .setInputData(inputData)
                .setInitialDelay(10, TimeUnit.SECONDS) // 맨 처음에 넣을 딜레이를 의미한다.
                .addTag("Check01")
                .build()
            workManager.enqueue(periodicWorkRequest)
        }
    }
}