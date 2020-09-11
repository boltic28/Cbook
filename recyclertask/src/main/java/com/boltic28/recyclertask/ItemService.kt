package com.boltic28.recyclertask

interface ItemService {

    fun setNewData(data: List<String>)
    fun removeItem(item: String)
    fun undoRemove(item: String)
    fun addItem(item: String)
    fun shuffle()
}