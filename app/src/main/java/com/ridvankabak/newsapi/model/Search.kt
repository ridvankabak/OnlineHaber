package com.ridvankabak.newsapi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Search(
    var title:String="",
    var content:String="",
    var language: String="",
    var to:String="",
    var from:String="",
    var toSort:String=""
):Parcelable