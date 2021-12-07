package com.example.nuntium.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.nuntium.networking.ApiService
import com.example.nuntium.pagesource.SourcePageByQuery
import javax.inject.Inject

class RepositorySearch @Inject constructor(
    private val apiService: ApiService
) {

    fun getArticles(query: String) = Pager(
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
    ).liveData

}