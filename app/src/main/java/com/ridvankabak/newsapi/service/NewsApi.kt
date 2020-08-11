package com.ridvankabak.newsapi.service

import com.ridvankabak.newsapi.model.NewsResponse
import io.reactivex.Single
import retrofit2.http.GET

interface NewsApi {

    //everything?q=bitcoin&apiKey=API_KEY
    @GET("top-headlines?country=tr&apiKey=04535619226e4a379d467ddc7ee8ed8d")
    fun getNews(): Single<NewsResponse>
}