package com.boltic28.cbook.presentation.models

import androidx.lifecycle.ViewModel
import com.boltic28.cbook.dagger.App
import com.boltic28.cbook.data.DataBase
import javax.inject.Inject

class MainActivityModel @Inject constructor(): ViewModel() {

    @Inject
    lateinit var dataBase: DataBase

    init {
        App.component.injectModel(this)
    }

    fun resetContact(){
        dataBase.resetContact()
    }

    fun getOne() = dataBase.getOne()
}