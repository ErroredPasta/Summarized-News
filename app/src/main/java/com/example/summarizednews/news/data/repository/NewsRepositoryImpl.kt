package com.example.summarizednews.news.data.repository

import com.example.summarizednews.news.core.di.Dispatcher
import com.example.summarizednews.news.data.api.NewsApi
import com.example.summarizednews.news.data.mapper.toNewsDetail
import com.example.summarizednews.news.data.mapper.toNewsList
import com.example.summarizednews.news.domain.model.News
import com.example.summarizednews.news.domain.model.NewsDetail
import com.example.summarizednews.news.domain.repository.NewsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,
    @Dispatcher(Dispatcher.Type.IO) private val dispatcher: CoroutineDispatcher
) : NewsRepository {

    override suspend fun getNewsList(page: Int): List<News> = withContext(dispatcher) {
        newsApi.getNewsList(page = page).toNewsList()
    }

    override suspend fun getNewsDetailById(id: String): NewsDetail = withContext(dispatcher) {
        newsApi.getNewsDetailById(id = id).toNewsDetail()
    }
}