package com.example.summarizednews.news.presentation

data class NewsUiState(
    val id: String,
    val title: String,
    val writtenAt: String,
    val section: String,
    val onClick: () -> Unit
)
