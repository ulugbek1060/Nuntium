package com.example.nuntium.repository

import com.example.nuntium.response.Article
import com.example.nuntium.db.dao.AppDao
import javax.inject.Inject

class RepositoryBookmark @Inject constructor(
    private val appDao: AppDao
) {

    fun getList() = appDao.getArticles()

    suspend fun delete(article: Article) = appDao.delete(article)

    suspend fun inset(article: Article) = appDao.insert(article)
}