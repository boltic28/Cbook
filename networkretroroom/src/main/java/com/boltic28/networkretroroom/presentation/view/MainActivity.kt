package com.boltic28.networkretroroom.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.boltic28.networkretroroom.R
import com.boltic28.networkretroroom.presentation.HumanAdapter
import com.boltic28.networkretroroom.presentation.model.MainModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val model: MainModel by lazy { ViewModelProviders.of(this).get(
        MainModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecycler()
        model.adapter = recycler.adapter as HumanAdapter
        model.loadData()
    }

    private fun initRecycler(){
        recycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val adapter =
            HumanAdapter(emptyList())

        recycler.adapter = adapter
    }
}
