package com.ridvankabak.newsapi.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ridvankabak.newsapi.model.Article
import com.ridvankabak.newsapi.model.News
import com.ridvankabak.newsapi.service.NewsDatabase
import com.ridvankabak.newsapi.service.ResultRoom
import com.ridvankabak.newsapi.service.ResultService
import kotlinx.coroutines.launch

class SaveViewModel(application: Application):BaseViewModel(application) {

    val result = MutableLiveData<ResultRoom>()

    fun getData(){
        launch {
            result.value = ResultRoom.GetNewsLoading(true)
            val dao = NewsDatabase(getApplication()).newsDao()
            val newsList = dao.getAllSaveNews()
            for (i in 0..(newsList.size-1)){
                Log.e("uuidASD",newsList[i].uuid.toString())
                Log.e("isSavedASD",newsList[i].isSaved.toString())
                Log.e("urlASD",newsList[i].url.toString())
            }
            showData(newsList)

        }

    }

    fun showData(newsList:List<News>){
        result.value = ResultRoom.GetNewsLoading(false)
        try{
            result.value = ResultRoom.GetNewsSuccess(newsList)
        }catch(exception:Exception){
            result.value = ResultRoom.GetNewsFail(true)
        }
    }

}