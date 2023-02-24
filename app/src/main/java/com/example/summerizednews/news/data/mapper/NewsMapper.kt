package com.example.summerizednews.news.data.mapper

import com.example.summerizednews.news.data.dto.detail.NewsDetailResponse
import com.example.summerizednews.news.data.dto.list.NewsListResponse
import com.example.summerizednews.news.domain.model.News
import com.example.summerizednews.news.domain.model.NewsDetail

internal fun NewsListResponse.toNewsList() = response.results.map {
    News(
        id = it.id,
        title = it.webTitle,
        writtenAt = it.webPublicationDate,
        section = it.sectionName,
        type = it.type
    )
}

internal fun NewsDetailResponse.toNewsDetail() = with(response.content) {
    NewsDetail(
        id = id,
        title = webTitle,
        section = sectionName,
        type = type,
        writtenAt = webPublicationDate,
        body = fields.body
    )
}