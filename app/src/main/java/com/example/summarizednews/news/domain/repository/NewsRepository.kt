package com.example.summarizednews.news.domain.repository

import androidx.paging.PagingData
import com.example.summarizednews.news.domain.model.News
import com.example.summarizednews.news.domain.model.NewsDetail
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNewsList(): Flow<PagingData<News>>
    fun reloadNewsList()
    suspend fun getNewsDetailById(id: String): NewsDetail
}