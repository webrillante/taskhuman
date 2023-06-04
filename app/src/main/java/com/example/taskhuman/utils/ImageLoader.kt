package com.example.taskhuman.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.taskhuman.R

object ImageLoader {

    fun loadImage(view: ImageView, image: String) {
        Glide.with(view)
            .load(image)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .centerCrop()
            .into(view)
    }
}