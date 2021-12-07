package com.example.nuntium.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nuntium.db.entity.Category
import com.example.nuntium.repository.RepositoryCategory
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoryViewModel @Inject constructor(private val repositoryCategory: RepositoryCategory) :
    ViewModel() {

    private val liveData = MutableLiveData<List<Category>>()


    init {
        getList()
    }

    fun getLiveData(): LiveData<List<Category>> {
        return liveData
    }

    private fun getList() = viewModelScope.launch {
        liveData.value = repositoryCategory.getList()
    }

    suspend fun update(category: Category) {
        repositoryCategory.update(category)
    }

    suspend fun getCount(): Int {
        return repositoryCategory.getCheckedCount()
    }

}