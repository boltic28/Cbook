package com.boltic28.cbook.presentation.interfaces

import androidx.lifecycle.LiveData
import com.boltic28.cbook.data.Process

interface ProcessControl {

    fun getLiveProcesses(): LiveData<List<Process>>

    fun startProcess(process: Process)

    fun updateProcess(process: Process) {
        updateLiveData()
    }

    fun finishProcess(process: Process)

    fun isExist(contactId: Long) : Boolean

    fun getProcessByContactIdIfExist(id: Long): Process?

    fun updateLiveData()
}