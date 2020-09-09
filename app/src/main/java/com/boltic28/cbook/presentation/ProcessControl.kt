package com.boltic28.cbook.presentation

import androidx.lifecycle.LiveData

interface ProcessControl {

    fun getLiveProcesses(): LiveData<List<Process>>
    fun startProcess(process: Process)
    fun updateProcess(process: Process)
    fun finishProcess(process: Process)
    fun isExistId(id: Long)
}