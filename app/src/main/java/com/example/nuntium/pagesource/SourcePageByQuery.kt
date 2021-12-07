package com.example.nuntium.pagesource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.nuntium.response.Article
import com.example.nuntium.networking.ApiService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SourcePageByQuery @Inject constructor(
    private val apiService: ApiService,
    private val query: String
) : PagingSource<Int, Article>() {

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
            val response = apiService.getByQuery(query = query, page = page)
            if (response.isSuccessful) {
                if (response.body()?.articles?.isEmpty()!!) {
                    LoadResult.Page(
                        data = listOf(),
                        prevKey = page - 1,
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
                    listOf(),
                    null,
                    null
                )
            }
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e.fillInStackTrace())
        }
    }
}