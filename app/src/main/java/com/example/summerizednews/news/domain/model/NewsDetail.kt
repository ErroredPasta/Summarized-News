package com.example.summerizednews.news.domain.model

data class NewsDetail(
    val id: String,
    val title: String,
    val section: String,
    val type: String,
    val writtenAt: String,
    val body: String
)
