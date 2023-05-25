package com.example.news_domain.repository

import androidx.paging.PagingData
import com.example.news_domain.model.News
import com.example.news_domain.model.NewsDetail
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNewsList(): Flow<PagingData<News>>
    fun reloadNewsList()
    suspend fun getNewsDetailById(id: String): NewsDetail
}