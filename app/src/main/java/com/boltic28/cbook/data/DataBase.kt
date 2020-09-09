package com.boltic28.cbook.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DataBase {

    companion object {
        const val TAG = "cBookt"
    }

    private var dbContacts = listOf(
        Contact(
            1L,
            "Bill Roberts",
            "+375 29 2223343",
            "cont1@gmail.com",
            "smth about him",
            "https://randomuser.me/api/portraits/women/26.jpg"
        ),
        Contact(
            2L,
            "John Roberts",
            "+375 29 3323343",
            "cont2@gmail.com",
            "smth about him",
            "path to photo"
        ),
        Contact(
            3L,
            "Henri Roberts",
            "+375 29 2113343",
            "cont3@gmail.com",
            "smth about him",
            "path to photo"
        ),
        Contact(
            4L,
            "Jim Roberts",
            "+375 29 2423343",
            "cont4@gmail.com",
            "smth about him",
            "path to photo"
        ),
        Contact(
            5L,
            "Alex Roberts",
            "+375 29 1223343",
            "cont5@gmail.com",
            "smth about him",
            "path to photo"
        ),
        Contact(
            6L,
            "Bruno Roberts",
            "+375 29 5623343",
            "cont6@gmail.com",
            "smth about him",
            "path to photo"
        ),
        Contact(
            7L,
            "Ron Roberts",
            "+375 29 5623343",
            "cont6@gmail.com",
            "smth about him",
            "path to photo"
        ),
        Contact(
            8L,
            "Serg Roberts",
            "+375 29 5623343",
            "cont6@gmail.com",
            "smth about him",
            "path to photo"
        ),
        Contact(
            9L,
            "Mark Roberts",
            "+375 29 5623343",
            "cont6@gmail.com",
            "smth about him",
            "path to photo"
        ),
        Contact(
            10L,
            "Linda Roberts",
            "+375 29 5623343",
            "cont6@gmail.com",
            "smth about him",
            "path to photo"
        ),
        Contact(
            11L,
            "Frank Roberts",
            "+375 29 5623343",
            "cont6@gmail.com",
            "smth about him",
            "path to photo"
        ),
        Contact(
            12L,
            "Regina Roberts",
            "+375 29 5623343",
            "cont6@gmail.com",
            "smth about him",
            "path to photo"
        ),
        Contact(
            13L,
            "Den Roberts",
            "+375 29 5623343",
            "cont6@gmail.com",
            "smth about him",
            "path to photo"
        ),
        Contact(
            14L,
            "Nick Roberts",
            "+375 29 5623343",
            "cont6@gmail.com",
            "smth about him",
            "path to photo"
        ),
        Contact(
            15L,
            "Grace Roberts",
            "+375 29 5623343",
            "cont6@gmail.com",
            "smth about him",
            "path to photo"
        ),
        Contact(
            16L,
            "Olya Roberts",
            "+375 29 5623343",
            "cont6@gmail.com",
            "smth about him",
            "path to photo"
        )
    )
    private var dbContact: Contact
    private var selectedContactId: Long

    init {
        dbContact = dbContacts[0]
        selectedContactId = dbContact.id
    }

    private val _mutableContact = MutableLiveData<Contact>()
    val contact: LiveData<Contact>
        get() = _mutableContact

    fun getSelectedContactId() = selectedContactId

    fun setContact(contact: Contact) {
        Log.d(TAG, "DB: choose contact: ${contact.name}")
        dbContact = contact
        selectedContactId = dbContact.id
    }

    fun setContact(id: Long){
        setContact(getById(id))
    }

    private fun getById(id: Long): Contact{
        dbContacts.forEach {
            if (it.id == id){
                Log.d(TAG, "DB: find contact with name ${it.name} by ID")
                return it
            }

        }
        return dbContacts[0]
    }

    fun getOne() = dbContact
    fun getAll() = dbContacts
}