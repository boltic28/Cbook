package com.boltic28.recyclertask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ItemAdapter(
    private var items: List<String>,
    private var listener: OnItemClickListener
) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    fun setData(list: List<String>) {
        items = list
    }

    fun getAtIndex(position: Int) = items[position]

    fun getPosition(item: String) = items.indexOf(item)

    fun reload() {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view, parent, false)
        )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var name: TextView = itemView.findViewById(R.id.item_name)
        private var picture: ImageView = itemView.findViewById(R.id.item_picture)

        fun bind(str: String) {

            if (str.contains("User ")) {
                name.text = str
                picture.visibility = View.GONE
            } else {
                name.visibility = View.GONE
                picture.visibility = View.VISIBLE
                val resourceId =
                    itemView.context.resources.getIdentifier(
                        str,
                        "drawable",
                        itemView.context.packageName
                    )
                picture.setImageResource(resourceId)
                checkSpaces(str)
            }

            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onClick(items[adapterPosition])
                }
            }
        }

        private fun checkSpaces(item: String) {
            if (getPosition(item) != itemCount - 1 &&
                !items[getPosition(item) + 1].contains("User ")
            ) {
                itemView.setPadding(0, 0, 0, 4)
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(item: String)
    }
}