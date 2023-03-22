package com.example.news_ui.mapper

import com.example.news_domain.model.News
import com.example.news_ui.screen.list.NewsUiState

internal fun News.toNewsUiState(
    onClick: () -> Unit
) = NewsUiState(
    id = id,
    title = title,
    writtenAt = writtenAt,
    section = section,
    onClick = onClick
)