package com.boltic28.cbook.presentation

import androidx.lifecycle.LiveData
import com.boltic28.cbook.data.Contact
import com.boltic28.cbook.data.Process

interface Worker {

    fun startWork(contact: Contact)
    fun getProcessFor(contact: Contact): Process?
    fun openContactFragment()

    fun mGetProcessFor(contact: Contact): LiveData<Process?>?
    fun deleteProcess(process: LiveData<Process?>)
}