package com.boltic28.networkretroroom.util

import androidx.recyclerview.widget.DiffUtil
import com.boltic28.networkretroroom.data.dto.Human

class DiffUtilUsers(private val oldList: List<Human>, private val newList: List<Human>): DiffUtil.Callback()  {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id &&
                oldList[oldItemPosition].gender == newList[newItemPosition].gender

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}