package com.example.summerizednews.news.domain.repository

import com.example.summerizednews.news.domain.model.News
import com.example.summerizednews.news.domain.model.NewsDetail

interface NewsRepository {
    suspend fun getNewsList(page: Int): List<News>
    suspend fun getNewsDetailById(id: String): NewsDetail
}