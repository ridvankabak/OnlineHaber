package com.ridvankabak.newsapi.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ridvankabak.newsapi.model.Search
import com.ridvankabak.newsapi.service.NewsDatabase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : BaseViewModel(application) {

    private val _searchLiveData = MutableLiveData<Search>()

    val searchLiveData: LiveData<Search>
        get() = _searchLiveData

    fun updateSearchValue(search: Search) {

        _searchLiveData.value = search
    }

    fun deleteAll(){
        launch {
            val dao = NewsDatabase(getApplication()).newsDao()
            dao.deleteAllNews()
        }
    }
}