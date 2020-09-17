package com.boltic28.recyclertask.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.boltic28.recyclertask.R
import com.boltic28.recyclertask.classes.BaseInstance
import com.boltic28.recyclertask.classes.PictureInstance
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class PictureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object{
        const val ID = R.layout.image_view

        fun create(parent: ViewGroup) = TextViewHolder(
            LayoutInflater.from(parent.context).inflate(TextViewHolder.ID, parent, false)
        )
    }

    private val picture: ImageView = itemView.findViewById(R.id.item_picture)

    fun bind(item: BaseInstance, listener: BaseAdapter.OnItemClickListener) {

        val resourceId =
            itemView.context.resources.getIdentifier(
                (item as PictureInstance).content,
                "drawable",
                itemView.context.packageName
            )
        Glide
            .with(itemView)
            .load(resourceId)
            .fitCenter()
            .transform(RoundedCorners(20))
            .into(picture)

        itemView.setOnClickListener {
            listener.onClick(item)
        }
    }
}