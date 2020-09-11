package com.boltic28.recyclertask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private var items: List<String>,
                  private var listener: OnItemClickListener) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    fun setData(list: List<String>){
        items = list
    }

    fun getAtIndex(position: Int) = items[position]

    fun getData() = items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view, parent, false)
        )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val name: TextView = itemView.findViewById(R.id.item_name)

        fun bind(str: String) {
            name.text = str

            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onClick(items[adapterPosition])
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(item: String)
    }
}