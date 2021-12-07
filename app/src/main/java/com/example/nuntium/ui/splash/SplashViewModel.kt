package com.example.nuntium.ui.splash

import androidx.lifecycle.ViewModel
import com.example.nuntium.db.entity.Category
import com.example.nuntium.repository.RepositorySplash
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val repositorySplash: RepositorySplash) :
    ViewModel() {


    suspend fun insetList(list: List<Category>) {
        repositorySplash.insetList(list)
    }

    suspend fun update(category: Category) {
        repositorySplash.update(category)
    }

}