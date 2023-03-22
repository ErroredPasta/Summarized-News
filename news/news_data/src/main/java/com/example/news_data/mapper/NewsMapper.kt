package com.example.news_data.mapper

import com.example.news_data.dto.detail.NewsDetailResponse
import com.example.news_data.dto.list.NewsListResponse
import com.example.news_domain.model.News
import com.example.news_domain.model.NewsDetail

internal fun NewsListResponse.toNewsList() = response.results.map {
    News(
        id = it.id,
        title = it.webTitle,
        writtenAt = it.webPublicationDate,
        section = it.sectionName
    )
}

internal fun NewsDetailResponse.toNewsDetail() = with(response.content) {
    NewsDetail(
        id = id,
        title = webTitle,
        section = sectionName,
        writtenAt = webPublicationDate,
        body = fields.body
    )
}