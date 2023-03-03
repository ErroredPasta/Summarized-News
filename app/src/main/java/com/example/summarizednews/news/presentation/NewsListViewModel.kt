package com.example.summarizednews.news.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.summarizednews.core.util.runCoroutineCatching
import com.example.summarizednews.news.domain.repository.NewsRepository
import com.example.summarizednews.news.presentation.mapper.toNewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {
    private val _state = MutableStateFlow(NewsListState(isLoading = true))
    val state = _state.asStateFlow()

    init {
        fetchNewsList()
    }

    fun fetchNewsList() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            runCoroutineCatching {
                /* TODO: 2023-02-25 토 23:09, implement paging */
                repository.getNewsList(page = 1)
            }.onSuccess { results ->
                val uiStates = results.map {
                    /* TODO: 2023-02-25 토 23:11, implement navigation */
                    it.toNewsUiState(onClick = {  })
                }
                _state.update { it.copy(isLoading = false, data = uiStates) }
            }
        }
    }
}

data class NewsListState(
    val isLoading: Boolean = false,
    val data: List<NewsUiState> = emptyList(),
    val error: Throwable? = null,
    val navigateTo: String? = null
)

