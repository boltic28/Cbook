package com.boltic28.cbook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActivityViewModel: ViewModel() {

    private val contacts = mutableListOf(
        Contact(
            1L,
            "Bill Roberts",
            "+375 29 2223343",
            "cont1@gmail.com",
            "smth about him",
            "path to photo"
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
            6L,
            "Bruno Roberts",
            "+375 29 5623343",
            "cont6@gmail.com",
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
            6L,
            "Bruno Roberts",
            "+375 29 5623343",
            "cont6@gmail.com",
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
            6L,
            "Bruno Roberts",
            "+375 29 5623343",
            "cont6@gmail.com",
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
            6L,
            "Bruno Roberts",
            "+375 29 5623343",
            "cont6@gmail.com",
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
            6L,
            "Bruno Roberts",
            "+375 29 5623343",
            "cont6@gmail.com",
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
        )
    )
    private var tmpContact = contacts[0]
    private var selectedPosition = 0

    fun getSelectedPosition() = selectedPosition

    fun getContact(): LiveData<Contact>{
        val data = MutableLiveData<Contact>()
        data.value = tmpContact
        return data
    }

    fun setContact(contact: Contact){
        tmpContact = contact
        selectedPosition = contacts.indexOf(contact)
    }

    fun getContacts(): LiveData<List<Contact>>{
        val data = MutableLiveData<List<Contact>>()
        data.value = contacts
        return data
    }
}