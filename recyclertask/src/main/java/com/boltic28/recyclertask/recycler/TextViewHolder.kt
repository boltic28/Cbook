package com.boltic28.recyclertask.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.boltic28.recyclertask.R
import com.boltic28.recyclertask.classes.BaseInstance
import com.boltic28.recyclertask.classes.TextInstance

class TextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object{
        const val ID = R.layout.text_view

        fun create(parent: ViewGroup) = TextViewHolder(
            LayoutInflater.from(parent.context).inflate(ID, parent, false)
        )
    }

    private val name: TextView = itemView.findViewById(R.id.item_name)

    fun bind(item: BaseInstance, listener: BaseAdapter.OnItemClickListener) {

        name.text = (item as TextInstance).content

        itemView.setOnClickListener {
            listener.onClick(item)
        }
    }
}