package com.ridvankabak.newsapi.service

import com.ridvankabak.newsapi.model.NewsResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    //everything?q=bitcoin&apiKey=API_KEY
    @GET("top-headlines?country=tr&apiKey=04535619226e4a379d467ddc7ee8ed8d")
    fun getNews(): Single<NewsResponse>

    @GET("everything")
    fun searchNews(@Query("qInTitle") title:String,
                   @Query("q") content:String,
                   @Query("from") from:String,
                   @Query("to") to:String,
                   @Query("language") language: String,
                   @Query("sortBy") sortBy:String,
                   @Query("apiKey") api_key:String): Single<NewsResponse>

    @GET("top-headlines")
    fun countryNews(@Query("country") country:String,
                @Query("apiKey") api_key:String):Single<NewsResponse>

    @GET("top-headlines")
    fun languageNews(@Query("language") language:String,
                     @Query("apiKey") api_key:String):Single<NewsResponse>
}