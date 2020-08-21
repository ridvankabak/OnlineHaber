package com.ridvankabak.newsapi.service

import com.ridvankabak.newsapi.model.News

sealed class ResultRoom {
    class GetNewsSuccess(val newses: List<News>) : ResultRoom()
    class GetNewsFail(val e: Boolean) : ResultRoom()
    class GetNewsLoading(val showLoading: Boolean) : ResultRoom()
}