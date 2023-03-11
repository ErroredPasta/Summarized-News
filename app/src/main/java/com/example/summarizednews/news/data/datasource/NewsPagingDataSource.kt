package com.example.summarizednews.news.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.summarizednews.core.util.runCoroutineCatching
import com.example.summarizednews.news.data.api.NewsApi
import com.example.summarizednews.news.data.mapper.toNewsDetail
import com.example.summarizednews.news.data.mapper.toNewsList
import com.example.summarizednews.news.domain.model.News
import com.example.summarizednews.news.domain.model.NewsDetail
import javax.inject.Inject

class NewsPagingDataSource @Inject constructor(
    private val api: NewsApi
) : PagingSource<Int, News>() {
    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        val page = params.key ?: 1

        return runCoroutineCatching {
            api.getNewsList(page)
        }.mapCatching { response ->
            val newsList = response.toNewsList()

            LoadResult.Page(
                data = newsList,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (newsList.isEmpty()) null else page + 1
            )
        }.getOrElse { cause ->
            LoadResult.Error(cause)
        }
    }

    suspend fun getNewsDetailById(id: String): NewsDetail {
        return api.getNewsDetailById(id = id).toNewsDetail()
    }

    companion object {
        const val PAGE_SIZE = 10
    }
}