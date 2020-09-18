package com.boltic28.networkretroroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.networkretroroom.`class`.Human
import com.boltic28.networkretroroom.dagger.AppDagger
import com.boltic28.networkretroroom.network.NetworkService
import com.boltic28.networkretroroom.service.HumanServiceImpl
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }
}
