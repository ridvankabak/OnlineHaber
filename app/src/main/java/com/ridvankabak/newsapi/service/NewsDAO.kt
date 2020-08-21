package com.ridvankabak.newsapi.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ridvankabak.newsapi.model.Article
import com.ridvankabak.newsapi.model.News

@Dao
interface NewsDAO {

    //Insert -> Room, insert into
    //suspend -> coroutine scope
    //vararg -> birden fazla ve istediğimiz sayıda
    //list<long> -> long, idler
    @Insert
    suspend fun insertAll(vararg news: Article):List<Long>

    @Query("SELECT * FROM article")
    suspend fun getAllNews() : List<Article>

    @Query("SELECT * FROM article WHERE uuid = :newsId")
    suspend fun getNews(newsId:Int) : Article?

    @Query("DELETE FROM article")
    suspend fun deleteAllNews()


    @Insert
    suspend fun insertSaveNews(vararg news:News)

    @Query("SELECT * FROM news")
    suspend fun getAllSaveNews(): List<News>

    @Query("DELETE FROM news WHERE uuid = :uuid")
    suspend fun deleteSaveNews(uuid: Int)

    @Query("SELECT * FROM news WHERE uuid=:uuid")
    suspend fun getSaveNews(uuid: Int): News?

    @Query("UPDATE news SET isSaved =:isSaved WHERE uuid=:uuid")
    suspend fun updateSaved(isSaved:Int,uuid: Int)

}