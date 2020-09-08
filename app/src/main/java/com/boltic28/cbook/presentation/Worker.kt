package com.boltic28.cbook.presentation

import com.boltic28.cbook.data.Contact
import com.boltic28.cbook.data.Process

interface Worker {

    fun startWork(contact: Contact)
    fun getProcessFor(contact: Contact): Process?
    fun openContactFragment()
}