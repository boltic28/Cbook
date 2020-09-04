package com.boltic28.cbook.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.boltic28.cbook.dagger.App
import com.boltic28.cbook.data.Contact
import com.boltic28.cbook.data.DataBase
import javax.inject.Inject

class MainActivityModel @Inject constructor(): ViewModel() {

    companion object {
        const val TAG = "cBook"
    }

    @Inject
    lateinit var dataBase: DataBase

    init {
        App.component.injectModel(this)
    }

    fun getContact(): LiveData<Contact>{
        Log.d(TAG, "MAM getContact")
        return dataBase.contact
    }

    fun getOne() = dataBase.getOne()
}