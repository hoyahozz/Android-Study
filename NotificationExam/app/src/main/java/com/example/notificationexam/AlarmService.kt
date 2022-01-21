package com.example.notificationexam

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast

class AlarmService : Service() {

    fun AlarmSoundService() {

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "알람 ON", Toast.LENGTH_LONG).show()
        return START_NOT_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}