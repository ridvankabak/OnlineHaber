package com.ridvankabak.newsapi.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.ridvankabak.newsapi.model.Article
import com.ridvankabak.newsapi.service.NewsDatabase
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application){
    val newsLiveData = MutableLiveData<Article>()

    fun roomGetData(uuid:Int){

        launch {
            val dao = NewsDatabase(getApplication()).newsDao()
            val news = dao.getNews(uuid)
            newsLiveData.value = news
        }
    }
}