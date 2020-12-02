package com.boltic28.coroutinen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.boltic28.coroutinen.databinding.ItemCoroutineBinding

class CoroutineAdapter : RecyclerView.Adapter<CoroutineHolder>() {

    private var items: MutableList<Work> = mutableListOf()

    fun add(item: Work) {
        items.add(item)
        notifyDataSetChanged()
    }

    fun stopAll(){
        items.forEach { it.stop() }
    }

    fun delete(item: Work) {
        items.remove(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoroutineHolder =
        CoroutineHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_coroutine,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CoroutineHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size
}

class CoroutineHolder(private val binding: ItemCoroutineBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Work) {
        binding.work = item
        binding.executePendingBindings()
    }
}