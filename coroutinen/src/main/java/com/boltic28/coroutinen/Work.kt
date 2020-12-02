package com.boltic28.coroutinen

import androidx.databinding.ObservableField
import androidx.databinding.ObservableLong
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.random.Random

class Work(
    val id: ObservableLong,
    val name: ObservableField<String>,
    val loading: ObservableField<String>,
    val status: ObservableField<String>,
    private val scope: CoroutineScope
) {

    lateinit var job: Job

    val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        writeLog("error handled")
    }

    fun createAsync(t1: Int, t2: Int) {
        job = scope.async(start = CoroutineStart.LAZY) {
            val deff1 = async {
                createSmth2(t1)
            }
            val deff2 = async {
                createSmth3(t2)
            }

            writeLog("result of work is: ${deff1.await()} & ${deff2.await()}")
        }
    }

    fun createSync(t1: Int, t2: Int) {
        job = scope.launch(start = CoroutineStart.LAZY) {
            val deff1 = createSmth2(t1)
            val deff2 = createSmth3(t2)

            writeLog("result of work is: $deff1 & $deff2")
        }
    }

    fun createCrWithOtherDispatcher() {
        job = scope.launch(Dispatchers.IO) {
            writeLog("dispatcher changed")
        }
    }

    fun runManyCoroutines(times: Int) {
        repeat(times) {
            scope.launch {
                writeLog("$it coroutine start")
                TimeUnit.SECONDS.sleep(2)
                writeLog("$it coroutine finish")
            }
        }
    }

    fun runCoroutineWithHandler(times: Int) {
        scope.launch(exceptionHandler) {
            writeLog("coroutine start")
            TimeUnit.SECONDS.sleep(2)
            writeLog("coroutine finish")
        }
    }

    suspend fun loadBigData(): String =
        suspendCoroutine {
            thread {
                TimeUnit.MILLISECONDS.sleep(5000)
                it.resume("data")
            }
        }

    fun start() {
        job.start()
    }

    fun stop() {
        job.cancel()
        writeLog("Stopped")
    }

    private suspend fun createSmth2(time: Int): String {
        writeLog("start 2nd")
        writeLog("wait for $time sec")
        delay(time * 1000L)
        createSmth("2nd")
        return "data 2"
    }

    private suspend fun createSmth3(time: Int): String {
        writeLog("start 3rd")
        writeLog("wait for $time sec")
        delay(time * 1000L)
        createSmth("3nd")
        return "data 3"
    }

    private suspend fun createSmth(name: String): String = suspendCoroutine { continuation ->
        val last = Random.nextInt(5, 15)
        writeLog("Loading $name")
        loading.set("job $name loading:")

        var i = 0
        while (i++ < last && job.isActive) {
            TimeUnit.SECONDS.sleep(1)
            loading()
        }

        loading.set("$name loaded")

        if (job.isActive) {
            writeLog("finish $name")
            continuation.resume("result $name")
        } else {
            continuation.resume("!!!FORCE STOP $name!!!")
        }
    }

    private fun loading() {
        val text = StringBuilder()
        text.append(loading.get())
        text.append("|")
        loading.set(text.toString())
    }

    private fun writeLog(msg: String) {
        val text = StringBuilder()
        text.append(status.get())
        text.append("\n")
        text.append(msg)
        status.set(text.toString())
    }
}