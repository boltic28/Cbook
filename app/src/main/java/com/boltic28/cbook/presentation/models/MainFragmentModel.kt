package com.boltic28.cbook.presentation.models

import android.util.Log
import androidx.lifecycle.ViewModel
import com.boltic28.cbook.dagger.App
import com.boltic28.cbook.data.Contact
import com.boltic28.cbook.data.DataBase
import javax.inject.Inject

class MainFragmentModel @Inject constructor(): ViewModel() {

    companion object {
        const val TAG = "cBookt"
    }

    @Inject
    lateinit var dataBase: DataBase

    init {
        App.component.injectModel(this)
    }

    fun setContact(contact: Contact){
        Log.d(TAG, "MFM setContact")
        dataBase.setContact(contact)
    }

    fun deleteContact(contact: Contact){
        dataBase.deleteContact(contact)
    }

    fun getSelectedId(): Long {
        Log.d(TAG, "MFM getSelectedId")
        return dataBase.getSelectedContactId()
    }

    fun getTestAll() = dataBase.getAll()
}