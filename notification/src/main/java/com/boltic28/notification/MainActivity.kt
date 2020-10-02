package com.boltic28.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    private val TAG = "testsf"

    private var pend: PendingIntent? = null
    private lateinit var manager: AlarmManager
    private lateinit var picker: TimePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val callback = TimePickerDialog.OnTimeSetListener { picker, h, m ->

            var hours = h.toString()
            var minutes = m.toString()
            if (hours.length == 1) hours = "0$hours"
            if (minutes.length == 1) minutes = "0$minutes"
            textView.text = "$hours:$minutes"
            Log.d(TAG, "picker : $picker \n hour: $hours \n minutes: $minutes")
            val time = LocalTime.of(h, m).minusNanos(LocalTime.now().toNanoOfDay()).second * 1000
            Log.d(TAG, "time before bell: ${time / 1000} s")

            pend = PendingIntent.getService(
                this,
                22,
                Intent(this, NotificationService::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time, pend)
        }
        picker = TimePickerDialog(this, callback, 0, 0, true)

        textView.setOnClickListener {
            picker.show()
        }

        button.setOnClickListener {
            textView.setText(R.string._00_00)
            pend?.let {
                Log.d(TAG, "timer is canceled")
                manager.cancel(it)
            }

        }

    }
}
