package com.example.summarizednews.news.domain.repository

import com.example.summarizednews.news.domain.model.News
import com.example.summarizednews.news.domain.model.NewsDetail

interface NewsRepository {
    suspend fun getNewsList(page: Int): List<News>
    suspend fun getNewsDetailById(id: String): NewsDetail
}