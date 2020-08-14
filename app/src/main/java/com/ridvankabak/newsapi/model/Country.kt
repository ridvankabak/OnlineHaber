package com.ridvankabak.newsapi.model

import com.ridvankabak.newsapi.R

class Country(var name:String, var flag: Int,var isSelected:Boolean = false) {

    object SupplierCountry{

        val country = arrayListOf(
            Country("Türkiye", R.drawable.ic_ulke_tr),
            Country("Almanya", R.drawable.ic_ulke_de),
            Country("Amerika", R.drawable.ic_ulke_us),
            Country("İspanya", R.drawable.ic_ulke_es),
            Country("İngiltere", R.drawable.ic_ulke_uk),
            Country("Fransa", R.drawable.ic_ulke_fr)

        )
    }
}