package com.example.summarizednews.news.presentation.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.summarizednews.core.util.runCoroutineCatching
import com.example.summarizednews.news.domain.model.NewsDetail
import com.example.summarizednews.news.domain.repository.NewsRepository
import com.example.summarizednews.summary.domain.repository.SummaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val summaryRepository: SummaryRepository,
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
                newsRepository.getNewsDetailById(newsId)
            }.onSuccess { newsDetail ->
                _state.update { it.copy(isLoading = false, data = newsDetail) }
                fetchSummary(content = newsDetail.body)
            }.onFailure { cause ->
                _state.update { it.copy(isLoading = false, error = cause) }
            }
        }
    }

    private suspend fun fetchSummary(content: String) {
        runCoroutineCatching {
            summaryRepository.summarize(content = content)
        }.onSuccess { summary ->
            _state.update { it.copy(summary = summary) }
        }.onFailure { cause ->
           _state.update { it.copy(error = cause) }
        }
    }
}

data class NewsDetailState(
    val isLoading: Boolean = false,
    val data: NewsDetail = emptyNewsDetail,
    val error: Throwable? = null,
    val summary: String? = null
)

private val emptyNewsDetail = NewsDetail(
    id = "",
    title = "",
    section = "",
    writtenAt = "",
    body = ""
)