package com.boltic28.recyclertask.recycler

import androidx.recyclerview.widget.DiffUtil
import com.boltic28.recyclertask.classes.BaseInstance

class DiffUtilItem(private val oldList: List<BaseInstance>, private val newList: List<BaseInstance>): DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}