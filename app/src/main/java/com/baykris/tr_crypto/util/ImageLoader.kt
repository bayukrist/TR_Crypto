package com.baykris.tr_crypto.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.baykris.tr_crypto.R

//Helper class for image loading
object ImageLoader {
    //Load image with Glide
    fun loadImage(
        view: ImageView,
        url: String,
        placeholder: Int = R.drawable.ic_baseline_image_24
    ) {
        Glide.with(view)
            .load(url)
            .placeholder(placeholder)
            .error(placeholder)
            .fallback(placeholder)
            .into(view)
    }
}