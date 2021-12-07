package com.example.nuntium.pagesource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.nuntium.response.Article
import com.example.nuntium.networking.ApiService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class SourcePageByCategory @Inject constructor(
    private val apiService: ApiService,
    private val category: String
) :
    PagingSource<Int, Article>() {
    private val STARTING_PAGE = 1

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: STARTING_PAGE
            val response = apiService.getByCategory(page = page, category = category)
            if (response.isSuccessful && response.code() in 200..299) {
                if (response.body()?.articles?.isEmpty()!!) {
                    LoadResult.Page(
                        data = listOf(),
                        prevKey = null,
                        nextKey = null
                    )
                } else {
                    LoadResult.Page(
                        data = response.body()?.articles ?: emptyList(),
                        prevKey = if (page == STARTING_PAGE) null else page - 1,
                        nextKey = if (response.body()?.articles?.isEmpty()!!) null else page + 1
                    )
                }
            } else {
                LoadResult.Page(
                    emptyList(),
                    null,
                    null
                )
            }
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }
}