package com.example.summarizednews.news.presentation.mapper

import com.example.summarizednews.news.domain.model.News
import com.example.summarizednews.news.presentation.screen.list.NewsUiState

internal fun News.toNewsUiState(
    onClick: () -> Unit
) = NewsUiState(
    id = id,
    title = title,
    writtenAt = writtenAt,
    section = section,
    onClick = onClick
)