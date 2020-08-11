package com.ridvankabak.newsapi.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ridvankabak.newsapi.model.Article

@Dao
interface NewsDAO {

    @Insert
    suspend fun insertAll(vararg news: Article):List<Long>

    //Insert -> Room, insert into
    //suspend -> coroutine scope
    //vararg -> birden fazla ve istediğimiz sayıda
    //list<long> -> long, idler

    @Query("SELECT * FROM article")
    suspend fun getAllNews() : List<Article>

    @Query("SELECT * FROM article WHERE uuid = :newsId")
    suspend fun getNews(newsId:Int) : Article

    @Query("DELETE FROM article")
    suspend fun deleteAllNews()
}