package com.example.nuntium.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.nuntium.db.dao.CategoryDao
import com.example.nuntium.db.entity.Category
import com.example.nuntium.networking.ApiService
import com.example.nuntium.pagesource.SourcePageByQuery
import javax.inject.Inject

class RepositoryHome @Inject constructor(
    private val categoryDao: CategoryDao,
    private val apiService: ApiService
) {

    fun getByQuery(query: String) = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 40,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            SourcePageByQuery(
                apiService,
                query
            )
        }
    ).flow

     fun getCategories() = categoryDao.getLiveData()

}