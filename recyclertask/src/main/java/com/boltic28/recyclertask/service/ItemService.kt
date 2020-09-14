package com.boltic28.recyclertask.service

import com.boltic28.recyclertask.classes.BaseInstance

interface ItemService {

    fun setNewData(data: List<BaseInstance>)

    fun removeItem(item: BaseInstance)
    fun removeItemUndo(item: BaseInstance)
    fun undoRemove(item: BaseInstance, index: Int)

    fun addItem(item: BaseInstance)
    fun addItem(item: BaseInstance, index: Int)

    fun shuffleItems()

    fun cancel()
}