package com.ridvankabak.newsapi.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ridvankabak.newsapi.model.Article
import com.ridvankabak.newsapi.model.News

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

    @Query("SELECT * FROM article WHERE url = :url")
    suspend fun getNews(url:String) : Article?

    @Query("DELETE FROM article")
    suspend fun deleteAllNews()


    @Insert
    suspend fun insertSaveNews(vararg news:News)

    @Query("SELECT * FROM news WHERE isSaved =:isSaved")
    suspend fun getAllSaveNews(isSaved: Int): List<News>

    @Query("DELETE FROM news WHERE url=:url")
    suspend fun deleteSaveNews(url:String)

    @Query("SELECT * FROM news WHERE url=:url")
    suspend fun getSaveNews(url: String): News?

    @Query("UPDATE news SET isSaved =:isSaved WHERE url=:url")
    suspend fun updateSaved(isSaved:Int,url: String)

}