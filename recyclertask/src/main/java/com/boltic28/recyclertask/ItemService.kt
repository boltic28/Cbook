package com.boltic28.recyclertask

interface ItemService {

    fun setNewData(data: List<String>)

    fun removeItem(item: String)
    fun removeItemUndo(item: String)
    fun undoRemove(item: String, index: Int)

    fun addItem(item: String)
    fun addItem(item: String, index: Int)

    fun shuffleItems()

    fun cancel()
}