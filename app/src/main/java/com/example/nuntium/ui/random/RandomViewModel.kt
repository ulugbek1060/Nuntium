package com.example.nuntium.ui.random

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.nuntium.response.Article
import com.example.nuntium.repository.RepositoryRandom
import kotlinx.coroutines.launch
import javax.inject.Inject

class RandomViewModel @Inject constructor(
    private val repository: RepositoryRandom
) : ViewModel() {

    fun getHeadlineVertical(): LiveData<PagingData<Article>> {
       return repository.getByQuery()
    }

    fun getHeadlineHorizontal(): LiveData<PagingData<Article>> {
        return repository.getByQuery()
    }

    suspend fun insert(article: Article?) {
        viewModelScope.launch {
            repository.insert(article)
        }
    }

    suspend fun remove(article: Article?) {
        viewModelScope.launch {
            repository.remove(article)
        }
    }

    suspend fun getArticle(article: Article): Article? {
        return repository.getArticle(article)
    }

}