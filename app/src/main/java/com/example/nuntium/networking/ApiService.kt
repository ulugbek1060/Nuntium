package com.example.nuntium.networking

import com.example.nuntium.response.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val KEY = "18a897e645f143fda4704dd9264f391b"

interface ApiService {

    @GET("v2/top-headlines")
    suspend fun getTopHeadline(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = KEY,
        @Query("page") page: Int = 1
    ): Response<NewsResponse>

    @GET("v2/top-headlines")
    suspend fun getByCategory(
        @Query("country") country: String? = "us",
        @Query("apiKey") apiKey: String? = KEY,
        @Query("category") category: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int? = 20,
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun getByQuery(
        @Query("q") query: String,
        @Query("sortBy") sortBy: String = "popularity",
        @Query("apiKey") key: String = KEY,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 20
    ): Response<NewsResponse>
}