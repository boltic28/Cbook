package com.boltic28.cbook.presentation.interfaces

import androidx.lifecycle.LiveData
import com.boltic28.cbook.data.Contact
import com.boltic28.cbook.data.Process

interface Worker {

    fun startWork(contact: Contact)
    fun getProcessFor(contact: Contact): Process?

    fun openContactFragment()
    fun setContactToolbar()

    fun mGetProcesses(): LiveData<List<Process>>?
    fun deleteProcess(process: Process)
}