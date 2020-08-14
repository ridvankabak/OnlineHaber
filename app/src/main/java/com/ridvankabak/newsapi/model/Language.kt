package com.ridvankabak.newsapi.model

import com.ridvankabak.newsapi.R

class Language(var name:String, var flag: Int,var isSelected:Boolean = false) {

    object SupplierLanguage{

        val language = arrayListOf(
            Language("Türkçe", R.drawable.ic_ulke_tr),
            Language("Almanca", R.drawable.ic_ulke_de),
            Language("İngilizce", R.drawable.ic_ulke_us),
            Language("İspanyolca", R.drawable.ic_ulke_es),
            Language("İngilizcee", R.drawable.ic_ulke_uk),
            Language("Fransızca", R.drawable.ic_ulke_fr)

        )
    }
}