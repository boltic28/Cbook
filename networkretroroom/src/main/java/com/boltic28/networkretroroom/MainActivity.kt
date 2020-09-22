package com.boltic28.networkretroroom

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.networkretroroom.dagger.AppDagger
import com.boltic28.networkretroroom.data.dto.Human
import com.boltic28.networkretroroom.data.network.NetworkService
import com.boltic28.networkretroroom.data.room.enum.Gender
import com.boltic28.networkretroroom.data.room.man.ManServiceImpl
import com.boltic28.networkretroroom.data.room.woman.WomanServiceImpl
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import trikita.log.Log
import java.util.stream.Collectors
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var network: NetworkService

    @Inject
    lateinit var manService: ManServiceImpl

    @Inject
    lateinit var womanService: WomanServiceImpl

    private var adapter: HumanAdapter = HumanAdapter(emptyList())
    private var isNetDataNotReady = true

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppDagger.component.injectActivity(this)

        recycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler.adapter = adapter

        getDataFromDB()

        network.getUsersFromNetWork(3)

        network.humans.observe(this,
            Observer {
                isNetDataNotReady = false
                adapter.setData(it)
                writeDataIntoDB(it)
            })
    }

    @SuppressLint("CheckResult")
    private fun getDataFromDB() {
        manService.getAll().subscribeOn(Schedulers.io()).subscribe { list ->
            if (isNetDataNotReady) {
                adapter.addAll(list.stream()
                    .map { man ->
                        manService.humanFrom(man)
                    }
                    .collect(Collectors.toList()))
            }
        }
        womanService.getAll().subscribeOn(Schedulers.io()).subscribe { list ->
            if (isNetDataNotReady) {
                adapter.addAll(list.stream()
                    .map { woman ->
                        womanService.humanFrom(woman)
                    }
                    .collect(Collectors.toList()))
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun writeDataIntoDB(humans: List<Human>) {
        manService.deleteAll()
            .subscribeOn(Schedulers.io())
            .mergeWith(womanService.deleteAll())
            .subscribeOn(Schedulers.io())
            .subscribe { deleted ->
                Log.d("deleted: $deleted")
            }

        humans.forEach { human ->
            if (human.gender == Gender.MAN) {
                manService.create(manService.manFrom(human))
                    .subscribeOn(Schedulers.io())
                    .subscribe { id ->
                        human.id = id
                    }
            } else {
                womanService.create(womanService.womanFrom(human))
                    .subscribeOn(Schedulers.io())
                    .subscribe { id ->
                        human.id = id
                    }
            }
        }
    }
}
