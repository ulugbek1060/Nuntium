package com.example.nuntium.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.nuntium.repository.RepositorySearch
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val repository: RepositorySearch
) : ViewModel() {

    private val state = SavedStateHandle()

    val currentSate = state.getLiveData("", "")

    @FlowPreview
    val query = currentSate.switchMap { query: String ->
        repository.getArticles(query).cachedIn(viewModelScope)
    }

    fun setQuery(query: String) {
        currentSate.value = query
    }
}