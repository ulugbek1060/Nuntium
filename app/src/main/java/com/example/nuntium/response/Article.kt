package com.example.nuntium.response

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Article(
    var author: String,
    var content: String,
    val description: String,
    var publishedAt: String,
    var source: Source,
    @PrimaryKey @NonNull
    val title: String,
    val url: String,
    var urlToImage: String
) : Serializable