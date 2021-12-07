package com.example.nuntium.repository

import com.example.nuntium.db.dao.CategoryDao
import com.example.nuntium.db.entity.Category
import javax.inject.Inject

class RepositorySplash @Inject constructor(private val categoryDao: CategoryDao) {

    suspend fun insetList(list: List<Category>) {
        categoryDao.insertOrUpdate(list)
    }

    suspend fun update(category: Category) {
        categoryDao.insertItem(category)
    }

}