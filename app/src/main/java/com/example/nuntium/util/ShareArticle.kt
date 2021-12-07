package com.example.nuntium.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.nuntium.response.Article

object ShareArticle {
    fun shareSubject(context: Context, bundle: Bundle, text: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        Intent.EXTRA_SUBJECT
        intent.putExtra(Intent.EXTRA_TEXT, text)
        ContextCompat.startActivity(context, intent, bundle)
    }

    fun formatterArticle(article: Article): String {
        val result = StringBuffer()
        result
            .append("title: ").append("\n")
            .append(article.title)
            .append("\n")
            .append("description: ").append("\n")
            .append(article.description).append("\n")
            .append("published time: ").append("\n")
            .append(article.publishedAt)
        return result.toString()
    }
}