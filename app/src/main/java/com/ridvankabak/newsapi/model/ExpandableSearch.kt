package com.ridvankabak.newsapi.model

class ExpandableSearch (var title:String, var isSelected:Boolean){

    object SupplierExpandable{

        val header = arrayListOf("Yeni Haberler")

        val releaseDate = arrayListOf(
            ExpandableSearch("Yeni Haberler", false),
            ExpandableSearch("En Çok Okunan", false),
            ExpandableSearch("Manşetler", false)
        )

        var hashExpandable  = hashMapOf(header.first() to  releaseDate)


    }

}