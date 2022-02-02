package com.example.notificationexam

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "AlarmReceiver"
        const val NOTIFICATION_ID = 123
        const val NOTIFICATION_CHANNEL = "alarmTest"
        const val NOTIFICATION_CHANNEL_NAME = "My Alarm"
    }

    override fun onReceive(context: Context, intent: Intent) {

        Log.d(TAG, "onReceive: ON")
        
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // 버전 분기 처리
            createNotificationChannel(context, notificationManager)
        }
        val minute = intent.getIntExtra("minute", 0)

        Log.d(TAG, "onReceive: $minute")
        val testIntent = Intent(context, MainActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent =
            PendingIntent.getActivity(context, minute, testIntent, PendingIntent.FLAG_IMMUTABLE)

//        val ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(
//            context,
//            RingtoneManager.TYPE_NOTIFICATION
//        )

        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("알림 제목")
            .setContentText("$minute")
//             .setSound(ringtoneUri) // 원하는 음악파일도 사용이 가능하다.
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

//        val notificationManagerCompat = NotificationManagerCompat.from(context)
//        notificationManagerCompat.notify(NOTIFICATION_ID, builder)

        notificationManager.notify(minute, builder)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(context: Context, notificationManager: NotificationManager) {
        notificationManager.createNotificationChannel(
            NotificationChannel(
                NOTIFICATION_CHANNEL,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                description = "Alarm Test"
            }
        )
    }
}