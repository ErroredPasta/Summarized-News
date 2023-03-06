package com.example.summarizednews.news.presentation.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.summarizednews.core.util.runCoroutineCatching
import com.example.summarizednews.news.domain.model.NewsDetail
import com.example.summarizednews.news.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val repository: NewsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(NewsDetailState(isLoading = true))
    val state = _state.asStateFlow()

    private val newsId = requireNotNull(savedStateHandle.get<String>("news_id"))

    init {
        fetchNewsDetail()
    }

    fun fetchNewsDetail() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            runCoroutineCatching {
                repository.getNewsDetailById(newsId)
            }.onSuccess { newsDetail ->
                _state.update { it.copy(isLoading = false, data = newsDetail) }
            }.onFailure { cause ->
                _state.update { it.copy(isLoading = false, error = cause) }
            }
        }
    }
}

data class NewsDetailState(
    val isLoading: Boolean = false,
    val data: NewsDetail = emptyNewsDetail,
    val error: Throwable? = null
)

private val emptyNewsDetail = NewsDetail(
    id = "",
    title = "",
    section = "",
    writtenAt = "",
    body = ""
)