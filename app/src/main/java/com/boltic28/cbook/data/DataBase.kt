package com.boltic28.cbook.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.boltic28.cbook.presentation.MainFragmentModel

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
//    private var processes = mutableListOf<MutableLiveData<Process>>()
    private var processes = mutableListOf<Process>()

    private val workingContacts = mutableListOf<Contact>()

    init {
        dbContact = dbContacts[0]
        selectedContactId = dbContact.id
    }

    private val _mutableContact = MutableLiveData<Contact>()
    val contact: LiveData<Contact>
        get() = _mutableContact

    private val _mutableContacts = MutableLiveData<List<Contact>>()
    val contacts: LiveData<List<Contact>>
        get() = _mutableContacts

    fun getSelectedContactId() = selectedContactId

    fun setContact(contact: Contact) {
        dbContact = contact
        selectedContactId = contact.id
    }

    fun getOne() = dbContact
    fun getAll() = dbContacts

    fun goWork(contact: Contact){
        workingContacts.add(contact)
    }

    fun setFree(contact: Contact){
        workingContacts.remove(contact)
    }

    fun addProcess(process: Process){
//        Log.d(TAG, "DB: add process: ${process.name}")
//        val mProcess = MutableLiveData<Process>()
//        mProcess.postValue(process)

        processes.add(process)
    }

    fun updateProcess(process: Process){
//        processes.forEach { pr->
//            if (pr.value?.id == process.id){
//                Log.d(TAG, "DB: update process: ${process.name} left: ${process.left()} sec.")
//                pr.postValue(process)
//            }
//        }
        processes.forEach { pr->
            if (pr.id == process.id){
                Log.d(TAG, "DB: update process: ${process.name} left: ${process.left()} sec.")
                pr.now = process.now
            }
        }
    }

    fun finishProcess(process: Process){
        Log.d(TAG, "DB: finish process: ${process.name}")
//        processes.forEach { pr->
//            if (pr.value?.id == process.id){
//                Log.d(TAG, "DB: delete process: ${process.name}")
//                processes.remove(pr)
//            }
//        }

        processes.remove(process)
    }

    fun getProcessFor(contact: Contact): Process?{ // change to LiveData
//        processes.forEach { pr->
//            if (pr.value?.id == contact.id){
//                return pr
//            }
//        }
        processes.forEach { pr->
            if (pr.id == contact.id){
                return pr
            }
        }
        return null
    }
}