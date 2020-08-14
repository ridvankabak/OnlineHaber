package com.ridvankabak.newsapi.service

import com.ridvankabak.newsapi.model.NewsResponse
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NewsApiService {

    val BASE_URL = "http://newsapi.org/v2/"
    val API_KEY = "04535619226e4a379d467ddc7ee8ed8d"

    private val api = getClient(BASE_URL).create(NewsApi::class.java)

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

    fun getClient(baseUrl:String):Retrofit{
        var interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}