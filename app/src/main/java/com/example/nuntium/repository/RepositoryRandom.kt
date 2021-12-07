package com.example.nuntium.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.nuntium.response.Article
import com.example.nuntium.db.dao.AppDao
import com.example.nuntium.networking.ApiService
import com.example.nuntium.pagesource.SourcePage
import javax.inject.Inject

class RepositoryRandom @Inject constructor(
    private val appDao: AppDao,
    private val apiService: ApiService
) {

    fun getByQuery() = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 40,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            SourcePage(apiService)
        }
    ).liveData

    suspend fun insert(article: Article?) {
        appDao.insert(article)
    }

    suspend fun remove(article: Article?) {
        appDao.delete(article)
    }


    suspend fun getArticle(article: Article): Article? {
        return appDao.getArticle(article.title)
    }
}