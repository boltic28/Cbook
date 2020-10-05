package com.boltic28.notification

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationService : Service() {

    companion object {
        const val TAG = "testsf"
        const val CHANNEL_ID = "NF09"
        const val CHANNEL_NAME = "notTask"
        const val CHANNEL_DESC = "work with notification"
        const val NOTIFICATION_ID = 454545
        const val SLEEP_FOR = 300_000
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return TimeServiceBinder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "SERVICE: onStartCommand")
        if (intent != null && intent.hasExtra("sleep")) {
            Log.d(TAG, "starting sleep command")
            sleep()
        } else {
            Log.d(TAG, "show notification")
            createNotification()
        }

        return START_REDELIVER_INTENT
    }

    private fun sleep() {
        Log.d(TAG, "timer will wait for ${SLEEP_FOR/60000} minutes")

        NotificationManagerCompat.from(this).cancel(NOTIFICATION_ID)
        val pend = PendingIntent.getService(
            this,
            22,
            Intent(this, NotificationService::class.java),
            PendingIntent.FLAG_ONE_SHOT
        )

        val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + SLEEP_FOR, pend)
    }

    private fun createNotification() {
        Log.d(TAG, "Creating notification")

        val intent = Intent(this, NotificationService::class.java)
        intent.putExtra("sleep", 300)

        val pend = PendingIntent.getService(
            this,
            22,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(this, CHANNEL_ID).apply {
            setContentTitle("Timer")
            setContentText("Time is up! Hurry!")
            setSmallIcon(R.drawable.ic_alarm)
            startForeground(NOTIFICATION_ID, this.build())
            addAction(R.drawable.ic_alarm, "SLEEP", pend)
            setAutoCancel(true)
        }

        NotificationManagerCompat.from(this).apply {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = CHANNEL_DESC
            getSystemService(NotificationManager::class.java)?.createNotificationChannel(channel)
        }
    }

    inner class TimeServiceBinder : Binder() {
        fun getService(): NotificationService {
            return this@NotificationService
        }
    }

}