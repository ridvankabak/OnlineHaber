package com.ridvankabak.newsapi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class News(
    @ColumnInfo(name = "author")
    val author: String?,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "url")
    val url: String?,

    @ColumnInfo(name = "urlToImage")
    val urlToImage: String?,

    @ColumnInfo(name = "publishedAt")
    val publishedAt: String?,

    @ColumnInfo(name = "content")
    val content: String?
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 100

    @ColumnInfo(name = "isSaved")
    var isSaved: Boolean = false
}