package com.example.nuntium.ui.blank

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.nuntium.repository.RepositoryBlank
import javax.inject.Inject

class BlankViewModel @Inject constructor(private val repositoryBlank: RepositoryBlank) :
    ViewModel() {

    private val state = SavedStateHandle()

    private val currentState = state.getLiveData("", "")

    val category = currentState.switchMap { query ->
        repositoryBlank.getByQuery(query)
    }

    fun getSourceByQuery(query: String) {
        currentState.value = query
    }
}