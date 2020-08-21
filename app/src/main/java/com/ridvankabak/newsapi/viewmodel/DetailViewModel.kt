package com.ridvankabak.newsapi.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ridvankabak.newsapi.model.Article
import com.ridvankabak.newsapi.model.News
import com.ridvankabak.newsapi.service.NewsDatabase
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application) {
    val newsLiveData = MutableLiveData<Article>()
    val newsSaveLiveData = MutableLiveData<News>()
    private val dao = NewsDatabase(getApplication()).newsDao()
    var news: News? = null

    fun roomGetData(uuid: Int) {
        launch {
            val new = dao.getNews(uuid)
            if (new != null) {
                newsLiveData.value = new

                news = News(
                    new.author,
                    new.title,
                    new.description,
                    new.url,
                    new.urlToImage,
                    new.publishedAt,
                    new.content
                )
                Log.e("HomeFragmentten","Geldi")
                Log.e("articleUuid",uuid.toString())
            } else {
                val getNewsSave = dao.getSaveNews(uuid)
                newsSaveLiveData.value = getNewsSave
                Log.e("SaveFragmentten","Geldi")
                Log.e("newsUuid",uuid.toString())

            }
        }
    }

    fun saveData() {
        launch {
            news?.let {
                dao.insertSaveNews(it)
                Log.e("isSaved", "Kaydedildi")
            }
        }
    }

    fun deleteData() {
        launch {
            news?.uuid.let {
                if (it != null) {
                    dao.deleteSaveNews(it)
                    Log.e("isSaved", "Çıkarıldı")
                }
            }

        }
    }

    fun updateData(isSaved: Int) {
        launch {
            when (isSaved) {
                0 -> news?.let {
                    dao.updateSaved(0, it.uuid)
                    Log.e("isSaved", "False")
                }
                1 -> news?.let {
                    dao.updateSaved(1, it.uuid)
                    Log.e("isSaved", "True")
                }


            }
        }

    }
}