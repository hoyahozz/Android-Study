package com.example.android.hilt.data

import dagger.hilt.android.scopes.ActivityScoped
import java.util.*
import javax.inject.Inject

// 컴포넌트의 범위를 Activity 컨테이너로 지정 -> Fragment 에서도 물론 사용가능.
@ActivityScoped
class LoggerInMemoryDataSource @Inject constructor() : LoggerDataSource {
    private val logs = LinkedList<Log>()

    override fun addLog(msg: String) {
        logs.addFirst(Log(msg, System.currentTimeMillis()))
    }

    override fun getAllLogs(callback: (List<Log>) -> Unit) {
        callback(logs)
    }

    override fun removeLogs() {
        logs.clear()
    }
}