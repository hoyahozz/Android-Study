package com.example.android.hilt.data

// 데이터 소스를 인터페이스로 추상화
// ButtonsFragment, LogsFragment 에서 사용된다.
interface LoggerDataSource {
    fun addLog(msg: String)
    fun getAllLogs(callback: (List<Log>) -> Unit)
    fun removeLogs()
}