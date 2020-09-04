package com.boltic28.cbook.presentation

import android.util.Log
import androidx.lifecycle.LiveData
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

    fun getContact(): Contact{
        Log.d(TAG, "MFM getContact")
        return dataBase.getOne()
    }

    fun setContact(contact: Contact){
        Log.d(TAG, "MFM setContact")
        dataBase.setContact(contact)
    }

    fun getAll(): LiveData<List<Contact>>{
        Log.d(TAG, "MFM getAllContacts")
        return dataBase.contacts
    }

    fun getSelectedId(): Long {
        Log.d(TAG, "MFM getSelectedId")
        return dataBase.getSelectedContactId()
    }

    fun getTestAll() = dataBase.getAll()
}