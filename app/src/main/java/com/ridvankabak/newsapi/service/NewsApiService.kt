package com.ridvankabak.newsapi.service

import com.ridvankabak.newsapi.model.NewsResponse
import io.reactivex.Single

class NewsApiService {

    val BASE_URL = "http://newsapi.org/v2/"
    val API_KEY = "04535619226e4a379d467ddc7ee8ed8d"

    private val api = RetrofitClient().getClient(BASE_URL).create(NewsApi::class.java)

    fun getSearchData(title:String, content:String,
                      to:String, from:String,
                      language:String, sortBy:String): Single<NewsResponse>{
        return api.searchNews(title,content,from,to,language,sortBy,API_KEY)
    }
    fun getData() : Single<NewsResponse>{
        return api.getNews()
    }

    fun getBottomDataCountry(country:String):Single<NewsResponse>{
        return api.countryNews(country,API_KEY)
    }

    fun getBottomDataLanguage(language:String):Single<NewsResponse>{
        return api.languageNews(language,API_KEY)
    }

}