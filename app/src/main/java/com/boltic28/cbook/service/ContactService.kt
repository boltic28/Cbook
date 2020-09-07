package com.boltic28.cbook.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.boltic28.cbook.R
import com.boltic28.cbook.dagger.App
import com.boltic28.cbook.data.Contact
import com.boltic28.cbook.data.DataBase
import com.boltic28.cbook.data.Process
import javax.inject.Inject

class ContactService @Inject constructor() : Service() {

    companion object {
        const val TAG = "cBookt"
        const val CHANNEL_ID = "CB09"
        const val CHANNEL_NAME = "cBook"
        const val CHANNEL_DESC = "work with contacts"
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
        App.component.injectService(this)
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val contact = intent?.getSerializableExtra("contact") as Contact

        startWork(contact)
        Log.d(TAG, "SERVICE: onStartCommand")
        return START_REDELIVER_INTENT
    }

    private fun createNotificationChannel() {
        Log.d(TAG, "SERVICE: CreateNotificationChannel")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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

    fun startWork(contact: Contact) {
        Log.d(TAG, "SERVICE: start work...")
        val timer = (20..25).shuffled().last()

        Thread(Runnable {
            val process = Process(contact.name, contact.id, timer)
            createNotification(process)

            dataBase.startProcess(process)

            while (process.now < process.timer) {
                Log.d(TAG, "SERVICE:  service counting ${process.now}")

                try {
                    Thread.sleep(990)
                    process.now++
                } catch (e: Throwable) {
                    Log.d(TAG, "ERROR counting")
                }
            }

            dataBase.finishProcess(process)
        }).start()
    }

    private fun createNotification(process: Process) {
        Thread(Runnable {
            Log.d(TAG, "SERVICE: createNotification")

            val notificationId = process.id.toInt()
            val builder = NotificationCompat.Builder(this, CHANNEL_ID).apply {
                setContentTitle(process.name)
                setContentText(resources.getString(R.string.notification_text, process.left()))
                setSmallIcon(R.drawable.ic_group)
                priority = NotificationCompat.PRIORITY_DEFAULT
                startForeground(notificationId, this.build())
            }


            NotificationManagerCompat.from(this).apply {
                builder.setProgress(process.timer, process.now, false)
                notify(notificationId, builder.build())


                while (process.now < process.timer) {
                    try {
                        Thread.sleep(990)
                    } catch (e: Throwable) {
                        Log.d(TAG, "ERROR counting")
                    }

                    Log.d(TAG, "SERVICE:  notification counting ${process.now}")
                    builder.setProgress(process.timer, process.now, false)
                        .setContentText(
                            resources.getString(
                                R.string.notification_text,
                                process.left()
                            )
                        )
                    notify(notificationId, builder.build())
                }
                builder.setContentText(resources.getString(R.string.notification_done))
                    .setProgress(0, 0, false)


                notify(notificationId, builder.build())
            }
        }).start()
    }

    inner class ContactServiceBinder : Binder() {
        fun getService(): ContactService {
            Log.d(TAG, "SERVICE: Binder")
            return this@ContactService
        }
    }
}