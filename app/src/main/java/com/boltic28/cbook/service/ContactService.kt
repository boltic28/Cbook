package com.boltic28.cbook.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.boltic28.cbook.R
import com.boltic28.cbook.data.Contact
import com.boltic28.cbook.data.DataBase
import com.boltic28.cbook.presentation.MainActivity
import javax.inject.Inject

class ContactService @Inject constructor(): Service() {

    companion object{
        const val TAG = "cBookt"
        const val CHANNEL_ID = "CB09"
        const val CHANNEL_NAME = "cBook"
        const val CHANNEL_DESC = "work with contacts"
        var notificationId = 0
    }

    @Inject
    lateinit var dataBase: DataBase

    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "SERVICE: onBind")
        return ContactServiceBinder()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "SERVICE: onUnBind")
        return super.onUnbind(intent)
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val contact = intent?.getSerializableExtra("contact")
//        Runnable {
//            val timer = (20..30).shuffled().last()
//            var now = 0
//
//            while (now < timer){
//                try {
//                    Thread.sleep(990)
//                    now++
//                }catch (e: Throwable){
//                    Log.d(TAG, "ERROR counting")
//                }
//            }
//
//        }.run()


        startWork()
        Log.d(TAG, "SERVICE: onStartCommand")
        return START_REDELIVER_INTENT
    }

    private fun createNotificationChannel(){
        Log.d(TAG, "SERVICE: CreateNotificationChannel")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            channel.description = CHANNEL_DESC

            val manager = getSystemService(NotificationManager::class.java)
            manager?.let { manager.createNotificationChannel(channel) }
        }
    }

    fun createNotification(){
        Log.d(TAG, "SERVICE: createNotification")
        val customView = RemoteViews(packageName, R.layout.notification_small)

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val pending = PendingIntent.getActivity(this,0,intent,0)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_group)
            .setContentIntent(pending)
            .setCustomContentView(customView)
            .setAutoCancel(false)
            .build()

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.let { manager.notify(notificationId++, notification) }
    }

    private fun startWork(){
        Log.d(TAG, "SERVICE: start work...")


        // start work with contact 20...30 sec
        // put it into notification's progress
    }

    inner class ContactServiceBinder: Binder(){
        fun getService(): ContactService {
            Log.d(TAG, "SERVICE: Binder")
            return this@ContactService
        }
    }
}