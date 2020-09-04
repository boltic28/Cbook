package com.boltic28.cbook.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.boltic28.cbook.dagger.App
import com.boltic28.cbook.data.Contact
import com.boltic28.cbook.data.DataBase
import javax.inject.Inject

class ContactFragmentModel @Inject constructor(): ViewModel() {

    companion object {
        const val TAG = "cBookt"
    }

    @Inject
    lateinit var dataBase: DataBase

    init {
        App.component.injectModel(this)
    }

    fun getContact(): Contact{
        Log.d(TAG, "CFM getContact")
        return dataBase.getOne()
    }

    fun setContact(contact: Contact){
        Log.d(TAG, "CFM setContact")
        dataBase.setContact(contact)
    }

    fun getAll(): LiveData<List<Contact>>{
        Log.d(TAG, "CFM getAllContacts")
        return dataBase.contacts
    }
}