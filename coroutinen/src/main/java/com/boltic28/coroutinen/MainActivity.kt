package com.boltic28.coroutinen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.databinding.ObservableLong
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.boltic28.coroutinen.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var job: Job = Job()

    //empty scope(context)
    private val scope = CoroutineScope(EmptyCoroutineContext)

    //way 1 to create context, main will start coroutine in main thread
    private val scope1 = CoroutineScope(Dispatchers.Main)

    //way 2 to create context, default 64 threads
    private val scope2 = CoroutineScope(job + Dispatchers.Default)

    //way 3 to create context, io count of threads equals count of cores
    private val itemForContext = ItemForContext(3L, "ivan", 3)
    private val scope3 = CoroutineScope(job + itemForContext + Dispatchers.IO)

    //you can create executor for 1 thread
    private val myDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    lateinit var binding: ActivityMainBinding

    private var adapter: CoroutineAdapter = CoroutineAdapter()
    private var id = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val recycler: RecyclerView = findViewById(R.id.recycler)
        recycler.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        recycler.adapter = adapter

        binding.logger = "waiting for command"
        binding.clicker = object : Clicker {
            override fun startAsync() {
                createAsyncCoroutine()
            }

            override fun startSync() {
                createSyncCoroutine()
            }

            override fun clear() {
                writeLog("stop coroutine command")
                adapter.stopAll()
            }
        }


        //way 3: getting data from context
        val user = scope3.coroutineContext[ItemForContext]
    }

    private fun createAsyncCoroutine() {
        val t1 = Random.nextInt(5, 10)
        val t2 = Random.nextInt(5, 15)
        writeLog("async create -> time1: $t1 s, time2: $t2 s")

        val work = createCoroutine()
        work.createAsync(t1, t2)
        adapter.add(work)
    }

    private fun createSyncCoroutine() {
        val t1 = Random.nextInt(5, 10)
        val t2 = Random.nextInt(5, 15)
        writeLog("sync create -> time1: $t1 s, time2: $t2 s")

        val work = createCoroutine()
        work.createSync(t1, t2)
        adapter.add(work)
    }

    private fun createCoroutine() = Work(
        ObservableLong(++id),
        ObservableField("coroutine $id"),
        ObservableField(""),
        ObservableField("Status"),
        scope2
    )

    private fun writeLog(msg: String) {
        val text = StringBuilder()
        text.append(binding.logger)
        text.append("\n")
        text.append(msg)
        binding.logger = text.toString()
    }

    override fun onDestroy() {
        scope2.cancel()
        super.onDestroy()
    }

}