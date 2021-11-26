package com.example.drug_app2021.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("image")
fun loadImage(view: ImageView, url: String){
    Glide.with(view)
        .load(url)
        .into(view)
    //使其圖片URL 轉為圖片顯示在view
}