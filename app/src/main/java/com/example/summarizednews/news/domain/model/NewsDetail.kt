package com.example.summarizednews.news.domain.model

data class NewsDetail(
    val id: String,
    val title: String,
    val section: String,
    val writtenAt: String,
    val body: String
)
