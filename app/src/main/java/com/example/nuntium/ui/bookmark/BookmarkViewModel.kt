package com.example.nuntium.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nuntium.response.Article
import com.example.nuntium.repository.RepositoryBookmark
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookmarkViewModel @Inject constructor(
    private val repository: RepositoryBookmark
) : ViewModel() {

    private lateinit var liveData: LiveData<List<Article>>

    fun getList(): LiveData<List<Article>> {
        liveData = repository.getList()
        return liveData
    }

    fun delete(article: Article) = viewModelScope.launch {
        repository.delete(article)
    }

    fun insert(article: Article) = viewModelScope.launch {
        repository.inset(article)
    }
}