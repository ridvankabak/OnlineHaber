package com.ridvankabak.newsapi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.ridvankabak.newsapi.model.Article
import com.ridvankabak.newsapi.model.NewsResponse
import com.ridvankabak.newsapi.model.Search
import com.ridvankabak.newsapi.service.NewsApiService
import com.ridvankabak.newsapi.service.NewsDatabase
import com.ridvankabak.newsapi.service.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : BaseViewModel(application) {

    val news = MutableLiveData<List<Article>>()
    val newsErrorMessage = MutableLiveData<Boolean>()
    val newsLoading = MutableLiveData<Boolean>()
    private var uptadeTime = 0.2 * 60 * 1000 * 1000 * 1000L

    private val newsApiService = NewsApiService()
    private val disposable = CompositeDisposable()
    private val sharedPreferencesHelper =
        SharedPreferencesHelper(getApplication())

    fun refreshData() {
        val recordedTime = sharedPreferencesHelper.getTime()


        if (recordedTime != null && recordedTime != 0L && System.nanoTime() - recordedTime < uptadeTime) {
            //Sqlitetan al
            getDataFromSQLite()

        } else {
            getDataFromInternet()
        }


    }

    private fun getDataFromSQLite() {
        newsLoading.value = true
        launch {
            val newsList = NewsDatabase(getApplication()).newsDao().getAllNews()
            newsShow(newsList)
            Toast.makeText(getApplication(), "Haberleri Room'dan aldık", Toast.LENGTH_LONG).show()
        }
    }

    private fun getDataFromInternet() {

        newsLoading.value = true

        disposable.add(
            newsApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NewsResponse>() {
                    override fun onSuccess(t: NewsResponse) {
                        //Başarılı olursa
                        saveSqlite(t.articles)
                        Toast.makeText(
                            getApplication(),
                            "Haberleri Internetten aldık",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onError(e: Throwable) {
                        //Hata alırsak
                        newsLoading.value = false
                        newsErrorMessage.value = true
                        e.printStackTrace()
                    }
                })
        )
    }

    fun refreshFromInternet() {
        getDataFromInternet()
    }

    private fun newsShow(newsList: List<Article>) {

        news.value = newsList
        newsLoading.value = false
        newsErrorMessage.value = false
    }

    private fun saveSqlite(newsList: List<Article>) {

        launch {
            val dao = NewsDatabase(getApplication()).newsDao()
            dao.deleteAllNews()
            val uuidListesi = dao.insertAll(*newsList.toTypedArray())
            var i = 0
            while (i < newsList.size) {
                newsList[i].uuid = uuidListesi[i].toInt()
                i++
            }

            newsShow(newsList)
        }

        sharedPreferencesHelper.saveTime(System.nanoTime())
    }

    fun searchData(search: Search?) {

        getSearchDataFromInternet(search)
    }

    fun getSearchDataFromInternet(search: Search?) {
        newsLoading.value = true

        disposable.add(
            newsApiService.getSearchData(
                search!!.title!!,
                search!!.content!!,
                search!!.to!!,
                search!!.from!!,
                search!!.language!!,
                search!!.toSort!!
            )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NewsResponse>() {
                    override fun onSuccess(t: NewsResponse) {
                        //Başarılı
                        saveSqlite(t.articles)
                        Toast.makeText(
                            getApplication(),
                            "Haberleri Internetten aldık search",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onError(e: Throwable) {
                        //Hata
                        newsLoading.value = false
                        newsErrorMessage.value = true
                        e.printStackTrace()
                    }

                })

        )
    }

    fun getBottomCountry(country: String) {
        newsLoading.value = true

        disposable.add(
            newsApiService.getBottomDataCountry(country)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NewsResponse>() {
                    override fun onSuccess(t: NewsResponse) {
                        //Başarılı olursa
                        if (t.totalResults == 0) {
                            newsLoading.value = false
                            newsErrorMessage.value = true
                        } else {
                            newsShow(t.articles)
                        }
                    }

                    override fun onError(e: Throwable) {
                        //Hata alırsak
                        newsLoading.value = false
                        newsErrorMessage.value = true
                        e.printStackTrace()
                    }
                })
        )
    }

    fun getBottomLanguage(language: String) {
        newsLoading.value = true

        disposable.add(
            newsApiService.getBottomDataLanguage(language)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<NewsResponse>() {
                    override fun onSuccess(t: NewsResponse) {
                        //Başarılı olursa
                        if (t.totalResults == 0) {
                            newsLoading.value = false
                            newsErrorMessage.value = true
                        } else {
                            newsShow(t.articles)
                        }
                    }

                    override fun onError(e: Throwable) {
                        //Hata alırsak
                        newsLoading.value = false
                        newsErrorMessage.value = true
                        e.printStackTrace()
                    }
                })
        )
    }

}