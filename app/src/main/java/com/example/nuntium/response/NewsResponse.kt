package com.example.nuntium.response

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)