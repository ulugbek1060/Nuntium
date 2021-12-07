package com.example.nuntium.repository

import com.example.nuntium.db.dao.CategoryDao
import com.example.nuntium.db.entity.Category
import javax.inject.Inject

class RepositoryCategory @Inject constructor(private val categoryDao: CategoryDao) {

    fun getAllCategory() = categoryDao.getLiveData()

    suspend fun update(category: Category) {
        categoryDao.insertItem(category)
    }

    suspend fun getList() = categoryDao.getList()

    suspend fun getCheckedCount(): Int {
        return categoryDao.getListCount()
    }
}