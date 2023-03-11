package com.example.summarizednews.news.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.summarizednews.core.di.Dispatcher
import com.example.summarizednews.news.data.datasource.NewsPagingDataSource
import com.example.summarizednews.news.domain.model.News
import com.example.summarizednews.news.domain.model.NewsDetail
import com.example.summarizednews.news.domain.repository.NewsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Provider

class NewsRepositoryImpl @Inject constructor(
    private val dataSourceProvider: Provider<NewsPagingDataSource>,
    @Dispatcher(Dispatcher.Type.IO) private val dispatcher: CoroutineDispatcher
) : NewsRepository {
    private val dataSource by lazy { dataSourceProvider.get() }

    override fun getNewsList(): Flow<PagingData<News>> {
        return Pager(
            config = PagingConfig(
                pageSize = NewsPagingDataSource.PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { dataSourceProvider.get() }
        ).flow
    }

    override fun reloadNewsList() {
        dataSource.invalidate()
    }

    override suspend fun getNewsDetailById(id: String): NewsDetail = withContext(dispatcher) {
        dataSource.getNewsDetailById(id = id)
    }
}