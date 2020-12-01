package com.boltic28.databind.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.boltic28.databind.R
import com.boltic28.databind.dto.interf.BaseItem

class ItemAdapter :
    RecyclerView.Adapter<ItemHolder>() {

    private var items: List<BaseItem> = emptyList()

    fun setData(list: List<BaseItem>){
        items = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder = ItemHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_person,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ItemHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size
}