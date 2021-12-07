package com.example.nuntium.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nuntium.response.Article
import com.example.nuntium.repository.RepositoryArticle
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArticleViewModel
@Inject constructor(
    private val repository: RepositoryArticle
) : ViewModel() {

    private val liveData = MutableLiveData(false)

    suspend fun getArticle(article: Article): LiveData<Boolean> {
        viewModelScope.launch {
            val article1 = repository.getArticle(article)
            liveData.value = article == article1
        }
        return liveData
    }

    fun insert(article: Article) {
        viewModelScope.launch {
            repository.insert(article)
        }
    }

    fun remove(article: Article) {
        viewModelScope.launch {
            repository.remove(article)
        }
    }
}