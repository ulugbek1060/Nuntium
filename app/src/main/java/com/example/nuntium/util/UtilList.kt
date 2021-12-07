package com.example.nuntium.util

import com.example.nuntium.R
import com.example.nuntium.response.Article
import com.example.nuntium.response.Source
import com.example.nuntium.db.entity.Category

object UtilList {
    fun getList(): List<Category> {
        val list = ArrayList<Category>()
        list.add(Category("\uD83D\uDC68\u200D\uD83D\uDCBC business", false))
        list.add(Category("\uD83C\uDF7F entertainment", false))
        list.add(Category("\uD83D\uDCAC general", false))
        list.add(Category("\uD83E\uDDD1\u200D⚕️ health", false))
        list.add(Category("\uD83E\uDDD1\u200D\uD83D\uDD2C science", false))
        list.add(Category("⚽ sports", false))
        list.add(Category("\uD83D\uDC68\u200D\uD83D\uDCBB technology", false))
        return list
    }

    fun getIntroductionList(): List<Int> {
        return listOf(R.drawable.img_1, R.drawable.img_2, R.drawable.img_3)
    }

    fun removeNullable(article: Article) {
        if (article.urlToImage == null) article.urlToImage = "0"
        if (article.author == null) article.author = "0"
        if (article.content == null) article.content = "0"
        if (article.publishedAt == null) article.publishedAt = "0"
        val name = article.source.name
        article.source = Source(name, name)
    }
}