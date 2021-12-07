package com.example.nuntium.repository

import com.example.nuntium.response.Article
import com.example.nuntium.db.dao.AppDao
import javax.inject.Inject

class RepositoryArticle
@Inject
constructor(
    private val appDao: AppDao
) {

    suspend fun getArticle(article: Article): Article? {
        return appDao.getArticle(article.title!!)
    }

    suspend fun remove(article: Article) {
        appDao.delete(article)
    }

    suspend fun insert(article: Article) {
        appDao.insert(article)
    }
}