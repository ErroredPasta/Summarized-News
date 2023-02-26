package com.example.summerizednews.news.presentation.mapper

import com.example.summerizednews.news.domain.model.News
import com.example.summerizednews.news.presentation.NewsUiState

internal fun News.toNewsUiState(
    onClick: () -> Unit
) = NewsUiState(
    id = id,
    title = title,
    writtenAt = writtenAt,
    section = section,
    onClick = onClick
)