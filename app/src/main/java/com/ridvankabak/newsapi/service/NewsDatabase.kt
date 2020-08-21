package com.ridvankabak.newsapi.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ridvankabak.newsapi.model.Article
import com.ridvankabak.newsapi.model.News

@Database(entities = [Article::class,News::class],version = 1)

abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao() : NewsDAO

    companion object{

        @Volatile private var instance : NewsDatabase? = null

        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(context.applicationContext,NewsDatabase::class.java,"newsdatabase").build()
    }
}