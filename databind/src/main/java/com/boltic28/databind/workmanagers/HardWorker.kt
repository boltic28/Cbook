package com.boltic28.databind.workmanagers

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class HardWorker(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {

    override fun doWork(): Result {
        try {
            val name = inputData.getString("name")
            val id = inputData.getLong("id", 0L)
            Log.d("workmng", "start work with $name id: $id")

            TimeUnit.SECONDS.sleep(4)

            val dataOut = Data.Builder()
                .putString("name", "done")
                .putInt("time", 111)
                .build()



        }catch (e: InterruptedException){
            Log.d("workmng", "error")
        }
        Log.d("workmng", "finish <------------!!!---------->")
        return Result.success()
    }
}