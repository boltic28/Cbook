package com.boltic28.cbook

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactItemAdapter (private val selectedPosition: Int,
    private val contacts: List<Contact>, private val listener: OnItemClickListener
) : RecyclerView.Adapter<ContactItemAdapter.ContactHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ContactHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false))

    override fun getItemCount() = contacts.size

    override fun onBindViewHolder(holder: ContactHolder, position: Int) = holder.bind(contacts[position])

    inner class ContactHolder(private val contactRow: View): RecyclerView.ViewHolder(contactRow){

        private val name = contactRow.findViewById<TextView>(R.id.item_name)

        @SuppressLint("ResourceAsColor")
        fun bind(contact: Contact){
            name.text = contact.name
            contactRow.isSelected = adapterPosition == selectedPosition

            contactRow.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onClick(contacts[adapterPosition])
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(contact: Contact)
    }
}