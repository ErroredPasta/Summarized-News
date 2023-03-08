package com.example.summarizednews.news.presentation.screen.list

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
                val uiStates = results.map { news ->
                    news.toNewsUiState(
                        onClick = {
                            _state.update { it.copy(navigateTo = news.id) }
                        }
                    )
                }

                _state.update { it.copy(isLoading = false, data = uiStates) }
            }.onFailure { cause ->
                _state.update { it.copy(isLoading = false, error = cause) }
            }
        }
    }

    fun navigationDone() {
        _state.update { it.copy(navigateTo = null) }
    }

    fun errorHandlingDone() {
        _state.update { it.copy(error = null) }
    }
}

data class NewsListState(
    val isLoading: Boolean = false,
    val data: List<NewsUiState> = emptyList(),
    val error: Throwable? = null,
    val navigateTo: String? = null
)

