package com.ridvankabak.newsapi.service

import com.ridvankabak.newsapi.model.Article

sealed class ResultService {

    class GetNewsSuccess(val newses: List<Article>) : ResultService()
    class GetNewsFail(val e: Boolean) : ResultService()
    class GetNewsLoading(val showLoading: Boolean) : ResultService()
}
