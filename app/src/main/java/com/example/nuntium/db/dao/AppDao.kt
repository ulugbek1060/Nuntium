package com.example.nuntium.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.nuntium.response.Article

@Dao
interface AppDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(article: Article?)

    @Delete
    suspend fun delete(article: Article?)

    @Query("SELECT * FROM ARTICLE")
    fun getArticles(): LiveData<List<Article>>

    @Query("SELECT * FROM ARTICLE WHERE title =:title")
    suspend fun getArticle(title: String): Article?

}