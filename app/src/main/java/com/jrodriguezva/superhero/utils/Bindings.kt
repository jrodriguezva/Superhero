package com.jrodriguezva.superhero.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jrodriguezva.superhero.R
import com.jrodriguezva.superhero.utils.extension.loadFromGif
import com.jrodriguezva.superhero.utils.extension.loadFromUrl

@BindingAdapter("items")
fun <T> RecyclerView.addItems(data: T) {
    if (this.adapter is BindableAdapter<*>) {
        (this.adapter as BindableAdapter<T>).setItems(data)
    }
}


@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) =
    url?.let { if (it.isNotBlank()) this.loadFromUrl(url, R.drawable.ic_broken_image) }

@BindingAdapter("gif")
fun ImageView.loadGif(@DrawableRes gif: Drawable) = this.loadFromGif(gif)

