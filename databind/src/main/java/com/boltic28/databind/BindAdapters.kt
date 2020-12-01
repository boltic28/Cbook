package com.boltic28.databind

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

@BindingAdapter("app:img")
fun loadImg(view: ImageView, url: String) {

    Glide
        .with(view)
        .load(url)
        .error(R.drawable.ic_person)
        .fitCenter()
        .transform(RoundedCorners(120))
        .into(view)
}

