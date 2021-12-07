package com.example.nuntium.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.nuntium.response.Article
import com.example.nuntium.db.dao.AppDao
import com.example.nuntium.networking.ApiService
import com.example.nuntium.pagesource.SourcePageByCategory
import javax.inject.Inject

class RepositoryBlank @Inject constructor(
    private val apiService: ApiService,
    private val appDao: AppDao
) {

    fun getByQuery(query: String) = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 40,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            SourcePageByCategory(
                apiService,
                query
            )
        }
    ).liveData

    suspend fun insertToDatabase(article: Article?) {
        appDao.insert(article)
    }

    suspend fun remove(article: Article?) {
        appDao.delete(article)
    }
}