package com.ridvankabak.newsapi.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ridvankabak.newsapi.model.News
import com.ridvankabak.newsapi.service.NewsDatabase
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application) {
    val newsLiveData = MutableLiveData<String>()
    val isSaved = MutableLiveData<Boolean>()
    val saveNews = MutableLiveData<News>()

    private val dao = NewsDatabase(getApplication()).newsDao()
    var newsSave: News? = null


    fun roomGetData(url: String) {
        launch {
            newsLiveData.value = url
            saveNews.value = dao.getSaveNews(url)
            if (saveNews.value == null) {
                Log.e("saveNews", "null")
                val news = dao.getNews(url)
                newsSave = News(
                    news?.author,
                    news?.title,
                    news?.description,
                    news?.url,
                    news?.urlToImage,
                    news?.publishedAt,
                    news?.content
                )
                isSaved.value = false
            } else {
                Log.e("saveNews", "Haber var -> " + saveNews.value!!.title.toString())
                isSaved.value = true
            }

        }
    }

    fun saveData() {
        launch {
            newsSave?.let {
                dao.insertSaveNews(it)
                Log.e("ASD isSaved", "Kaydedildi")
            }
        }
    }

    fun deleteData() {
        launch {
            dao.deleteSaveNews(saveNews.value?.url!!)
                Log.e("ASD isSaved", "Çıkarıldı")


        }
    }

    fun updateData(isSaved: Int) {
        launch {
            when (isSaved) {

                1 -> newsSave?.let {
                    it.url?.let { it1 -> dao.updateSaved(1, it1) }
                    Log.e("ASD isSaved", "True")
                }


            }
        }

    }
}

