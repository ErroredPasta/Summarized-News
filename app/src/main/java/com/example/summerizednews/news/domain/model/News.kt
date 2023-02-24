package com.example.summerizednews.news.domain.model

data class News(
    val id: String,
    val title: String,
    val writtenAt: String,
    val section: String,
    val type: String
)
