package com.boltic28.cbook.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.boltic28.cbook.R
import com.boltic28.cbook.data.Contact
import com.boltic28.cbook.data.DataBase
import com.boltic28.cbook.data.Process
import com.boltic28.cbook.presentation.MainActivity
import javax.inject.Inject

class ContactService @Inject constructor() : Service() {

    companion object {
        const val TAG = "cBookt"
        const val CHANNEL_ID = "CB09"
        const val CHANNEL_NAME = "cBook"
        const val CHANNEL_DESC = "work with contacts"
        var processes = mutableListOf<Process>()
        var mProcesses = mutableListOf<MutableLiveData<Process?>>()
    }

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
        val contact = intent?.getSerializableExtra("contact") as Contact

        startWork(contact)
        Log.d(TAG, "SERVICE: onStartCommand")
        return START_REDELIVER_INTENT
    }

    fun startWork(contact: Contact) {
        Log.d(TAG, "SERVICE: start work...")
        val timer = (25..35).shuffled().last()

        Thread(Runnable {
            val process = Process(contact.name, contact.id, timer)
            createNotification(process)

            startProcess(process)

            while (process.now < process.timer) {
                try {
                    Thread.sleep(990)
                    process.now++
// live                   refreshProcess(process)
                } catch (e: Throwable) {
                    Log.d(TAG, "ERROR counting")
                }
            }

            finishProcess(process)
        }).start()
    }

    fun getProcessFor(contact: Contact): Process? {
        processes.forEach { pr ->
            if (pr.id == contact.id) {
                Log.d(
                    DataBase.TAG,
                    "SERVICE: process for ${contact.name} is founded, left: ${pr.left()}s"
                )
                return pr
            }
        }
        Log.d(
            DataBase.TAG,
            "SERVICE: process for ${contact.name} is not founded: size: ${processes.size}"
        )
        return null
    }

    //live
    fun mGetProcessFor(contact: Contact): LiveData<Process?>? {
        mProcesses.forEach { pr ->
            if (pr.value?.id == contact.id) {
                return pr
            }
        }
        return null
    }
    //live
    fun deleteProcess(process: LiveData<Process?>){
        mProcesses.forEach { pr ->
            if (pr == process) {
                mProcesses.remove(pr)
            }
        }
    }

    private fun startProcess(process: Process) {
        Log.d(DataBase.TAG, "SERVICE: start process: ${process.name}, size: ${processes.size}")
        processes.add(process)
        //live
        val mProcess = MutableLiveData<Process>()
        mProcess.postValue(process)
    }
        //live
    private fun refreshProcess(process: Process) {
        Log.d(DataBase.TAG, "SERVICE: start process: ${process.name}, size: ${processes.size}")

        mProcesses.forEach { pr ->
            if (pr.value?.id == process.id) {
                pr.postValue(process)
            }
        }
    }

    private fun finishProcess(process: Process) {
        Log.d(DataBase.TAG, "SERVICE: finish process: ${process.name}")
        processes.remove(process)
        //live
        mProcesses.forEach { pr ->
            if (pr.value == process) {
                pr.postValue(null)
            }
        }

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

    private fun createNotification(process: Process) {
        Thread(Runnable {
            Log.d(TAG, "SERVICE: createNotification")

            val bundle = Bundle()
            Log.d(TAG, "SERVICE: ${process.name} id ${process.id} was putted in bundle ")
            bundle.putLong("id", process.id)

            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                putExtras(bundle)
                putExtra("id", process.id)
            }

            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val notificationId = process.id.toInt()
            val builder = NotificationCompat.Builder(this, CHANNEL_ID).apply {
                setContentTitle(process.name)
                setContentText(resources.getString(R.string.notification_text, process.left()))
                setSmallIcon(R.drawable.ic_group)
                setContentIntent(pendingIntent)
                priority = NotificationCompat.PRIORITY_DEFAULT
                startForeground(notificationId, this.build())
            }

            NotificationManagerCompat.from(this).apply {
                builder.setProgress(process.timer, process.now, false)
                notify(notificationId, builder.build())

                while (process.now < process.timer) {
                    try {
                        Thread.sleep(990)
                        builder.setProgress(process.timer, process.now, false)
                            .setContentText(
                                resources.getString(
                                    R.string.notification_text,
                                    process.left()
                                )
                            )
                        notify(notificationId, builder.build())
                    } catch (e: Throwable) {
                        Log.d(TAG, "ERROR counting")
                    }
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