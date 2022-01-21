package com.example.notificationexam

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import java.text.SimpleDateFormat
import java.util.*

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private lateinit var mAlarmManager : AlarmManager

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        mAlarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val cal = Calendar.getInstance()
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)

        return TimePickerDialog(requireContext(), this, hour, minute, true)
        // DateFormat.is24HourFormat -> 기기의 24시간 셋을 맞추겠다는 의미.
    }

    // 유저가 시간 선택 시 호출
    override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // createNotificationChannel()
        Log.d(TAG, "onTimeSet: ON")

        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
        cal.set(Calendar.MINUTE, minute)

        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(requireContext(), 123, intent, 0)

//        val pendingIntent =
//            PendingIntent.getActivity(requireContext(), 123, intent, 0)

//        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pendingIntent)

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent)

        // ----------------------------------------------------------------------------

        val currentDateTime = cal.time
        val dateText = SimpleDateFormat(
            "yyyy년 MM월 dd일 EE요일 a hh시 mm분",
            Locale.getDefault()
        ).format(currentDateTime)

        Toast.makeText(
            requireContext(), "$dateText 으로 시간 설정", Toast.LENGTH_LONG
        ).show()
        Log.d(TAG, "onTimeSet: $dateText")

    }

//    private fun createNotificationChannel() {
//        val manager: NotificationManager =
//            requireContext().getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // 버전 분기 처리
//            manager.createNotificationChannel(
//                NotificationChannel(
//                    "default",
//                    "기본 채널",
//                    NotificationManager.IMPORTANCE_HIGH
//                )
//            )
//        }
//    }

    companion object {
        private const val TAG = "TimePickerFragment"
    }

}