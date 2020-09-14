package com.boltic28.recyclertask.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boltic28.recyclertask.R
import com.boltic28.recyclertask.classes.BaseInstance

class BaseAdapter(var items: List<BaseInstance>,
                  private var listener: OnItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun setData(list: List<BaseInstance>) {
        items = list
    }

    fun getAtIndex(position: Int) = items[position]

    fun reload() {
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int) = items[position].type

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            101 -> {
                return TextViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.text_view,
                            parent,
                            false
                        )
                )
            }
            102 -> {
                return PictureViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.image_view,
                            parent,
                            false
                        )
                )
            }
        }
        return TextViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_view,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            101 -> {
                (holder as TextViewHolder).bind(items[position], listener)
            }
            102 -> {
                (holder as PictureViewHolder).bind(items[position], listener)
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(item: BaseInstance)
    }
}