package com.boltic28.networkretroroom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.boltic28.networkretroroom.`class`.Human
import com.boltic28.networkretroroom.`class`.Man
import com.boltic28.networkretroroom.`class`.Woman

//class HumanAdapter(var items: List<Human>): RecyclerView.Adapter<HumanAdapter.HumanHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
//        HumanHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false))
//
//    override fun getItemCount() = items.size
//
//    override fun onBindViewHolder(holder: HumanHolder, position: Int) {
//        holder.bind(items[position])
//    }
//
//    inner class HumanHolder(itemView: View): RecyclerView.ViewHolder(itemView){
//
//        var id: TextView = itemView.findViewById(R.id.item_id)
//        var name: TextView = itemView.findViewById(R.id.item_name)
//        var lastName: TextView = itemView.findViewById(R.id.item_last_name)
//        var email: TextView = itemView.findViewById(R.id.item_mail)
//
//
//
//        fun  bind(item: Human){
////            (item as Man).let {
////                id.text = item.id.toString()
////                name.setText(item.name)
////                lastName.setText(item.lastName)
////                email.setText(item.mail)
////            }
////            (item as Woman).let {
////                id.text = item.id.toString()
////                name.setText(item.name)
////                lastName.setText(item.lastName)
////                email.setText(item.mail)
////            }
//        }
//
//    }
//}

