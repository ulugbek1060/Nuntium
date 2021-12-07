package com.example.nuntium.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.nuntium.db.entity.Category
import com.example.nuntium.repository.RepositoryHome
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repositoryHome: RepositoryHome) : ViewModel() {

    private lateinit var liveData: LiveData<List<Category>>

    fun getCategoryList(): LiveData<List<Category>> {
        liveData = repositoryHome.getCategories()
        return liveData
    }


}