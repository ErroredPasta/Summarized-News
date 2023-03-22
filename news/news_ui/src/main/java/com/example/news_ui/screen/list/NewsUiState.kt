package com.example.news_ui.screen.list

data class NewsUiState(
    val id: String,
    val title: String,
    val writtenAt: String,
    val section: String,
    val onClick: () -> Unit
)
