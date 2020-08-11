package com.ridvankabak.newsapi.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ridvankabak.newsapi.R
import im.delight.android.webview.AdvancedWebView

fun ImageView.downloadImage(url:String?,placeholder:CircularProgressDrawable){

    val options = RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher_round)

    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}

fun makePlaceHolder(context:Context):CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

@BindingAdapter("android:downloadImage")
fun downloadImg(view:ImageView,url:String?){
    view.downloadImage(url, makePlaceHolder(view.context))
}
