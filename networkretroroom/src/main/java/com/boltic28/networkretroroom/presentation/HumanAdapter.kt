package com.boltic28.networkretroroom.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.boltic28.networkretroroom.R
import com.boltic28.networkretroroom.data.dto.Human
import com.boltic28.networkretroroom.util.DiffUtilUsers
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class HumanAdapter(var items: List<Human>) : RecyclerView.Adapter<HumanAdapter.HumanHolder>() {

    private fun setData(list: List<Human>) {
        items = list
        notifyDataSetChanged()
    }

    fun refreshData(list: List<Human>) {
        val itemDiff = DiffUtilUsers(items, list)
        val result = DiffUtil.calculateDiff(itemDiff)

        setData(list)
        result.dispatchUpdatesTo(this)
    }

    fun addAll(list: List<Human>) {
        val newData = mutableListOf<Human>()
        newData.addAll(items)
        newData.addAll(list)
        items = newData
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HumanHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: HumanHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class HumanHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var id: TextView = itemView.findViewById(R.id.item_id)
        var name: TextView = itemView.findViewById(R.id.item_name)
        var lastName: TextView = itemView.findViewById(R.id.item_last_name)
        var email: TextView = itemView.findViewById(R.id.item_mail)
        var age: TextView = itemView.findViewById(R.id.item_age)
        var phone: TextView = itemView.findViewById(R.id.item_phone)
        var gender: TextView = itemView.findViewById(R.id.item_gender)
        var image: ImageView = itemView.findViewById(R.id.item_image)


        fun bind(item: Human) {
            id.text = item.id.toString()
            name.text = item.name
            lastName.text = item.lastName
            email.text = item.mail
            age.text = item.age.toString()
            phone.text = item.phone
            gender.text = item.gender.value

            Glide
                .with(itemView)
                .load(item.photo)
                .fitCenter()
                .transform(RoundedCorners(120))
                .into(image)
        }

    }
}

