package com.boltic28.cbook.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.boltic28.cbook.data.Contact

class ContactDiffUtil(private val oldList: List<Contact>, private val newList: List<Contact>): DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldContact = oldList[oldItemPosition]
        val newContact = newList[newItemPosition]
        return oldContact.id == newContact.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldContact = oldList[oldItemPosition]
        val newContact = newList[newItemPosition]
        return oldContact.name == newContact.name &&
                oldContact.mail == newContact.mail &&
                oldContact.number == newContact.number &&
                oldContact.remark == oldContact.remark &&
                oldContact.photo == oldContact.photo
    }
}