package com.example.notificationexam

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun createNotification(view: View) {
        show()
    }

    fun show() {
        val builder = NotificationCompat.Builder(this, "default")
        // compat 을 사용하는 이유는 버전의 하위호환이 되기 때문이다. 즉, 버전분기를 신경쓰지 않고 작성해도 된다.
        // 그러나 알림 채널 등록은 버전분기를 타야하는 것은 똑같음.
        // 오레오부터는 채털 아이디를 꼭 설정해주어야 함. 기존 버전은 설정하지 않아도 괜찮다.

        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setContentTitle("알림 제목")
        builder.setContentText("알림 세부 텍스트")

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        builder.setContentIntent(pendingIntent)
        // PendingIntent 안의 intent 가 실행된다.

        val largeIcon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
        // 큰 아이콘은 비트맵으로 변환 후 사용해야 함.
        builder.setLargeIcon(largeIcon)
        builder.setColor(Color.RED)

        val ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(
            this,
            RingtoneManager.TYPE_NOTIFICATION
        )
        // 기기에서 제공하는 알람 사용

        builder.setSound(ringtoneUri) // 원하는 음악파일도 사용이 가능하다.

        val vibrate: LongArray = longArrayOf(0, 100, 200, 300) // 진동 설정, 진동의 규칙 지정
        builder.setVibrate(vibrate)

        builder.setAutoCancel(true)
        // Notification 클릭 시 앱이 실행되면서 사라지게 할 것인지, 사라지지 않게 할 것인지 설정

        val manager: NotificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // 버전 분기 처리
            manager.createNotificationChannel(
                NotificationChannel(
                    "default",
                    "기본 채널",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }

        manager.notify(1, builder.build())
    }

    // 노티 해제
    fun removeNotification(view: View) {
        hide()
    }

    fun hide() {
        NotificationManagerCompat.from(this).cancel(1)
        // = val manager : NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        // 같은 코드임
    }
}