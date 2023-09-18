package com.ktln.kotlinpulse.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.ktln.kotlinpulse.R

fun ImageView.downloadImg(url:String?){
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.music)
        .error(R.drawable.dead)
        .into(this)
}