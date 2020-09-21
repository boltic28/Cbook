package com.boltic28.networkretroroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.networkretroroom.dagger.AppDagger
import com.boltic28.networkretroroom.data.Human
import com.boltic28.networkretroroom.network.HttpService
import com.boltic28.networkretroroom.network.NetworkService
import com.boltic28.networkretroroom.service.man.ManServiceImpl
import com.boltic28.networkretroroom.service.woman.WomanServiceImpl
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var network: NetworkService
    @Inject
    lateinit var manService: ManServiceImpl
    @Inject
    lateinit var womanService: WomanServiceImpl

    lateinit var people: List<Human>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppDagger.component.injectActivity(this)

//        people = network.getUsersFromNetWork(5)
        people = HttpService().getUsersFromNet(5)

        recycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        recycler.adapter = HumanAdapter(people)
    }
}
