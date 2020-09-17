package com.boltic28.recyclertask.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boltic28.recyclertask.classes.BaseInstance

class BaseAdapter(var items: List<BaseInstance>, private var listener: OnItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun setData(list: List<BaseInstance>) {
        items = list
    }

    fun getAtIndex(position: Int) = items[position]

    fun reload() = notifyDataSetChanged()

    override fun getItemViewType(position: Int) = items[position].type

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == PictureViewHolder.ID){
            PictureViewHolder.create(parent)
        }else{
            TextViewHolder.create(parent)
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            TextViewHolder.ID -> (holder as TextViewHolder).bind(items[position], listener)
            PictureViewHolder.ID -> (holder as PictureViewHolder).bind(items[position], listener)
        }
    }

    interface OnItemClickListener {
        fun onClick(item: BaseInstance)
    }
}