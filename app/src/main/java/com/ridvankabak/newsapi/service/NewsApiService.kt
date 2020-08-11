package com.ridvankabak.newsapi.service

import com.ridvankabak.newsapi.model.NewsResponse
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NewsApiService {

    private val BASE_URL = "http://newsapi.org/v2/"

    private val api = getClient(BASE_URL).create(NewsApi::class.java)

    fun getData() : Single<NewsResponse>{
        return api.getNews()
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