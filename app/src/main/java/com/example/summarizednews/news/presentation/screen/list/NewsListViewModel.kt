package com.example.summarizednews.news.presentation.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.summarizednews.news.domain.repository.NewsRepository
import com.example.summarizednews.news.presentation.mapper.toNewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {
    private val pagingDataFlow = repository.getNewsList().map { pagingData ->
        otherStatesFlow.update { it.copy(isLoading = false) }

        pagingData.map { news ->
            news.toNewsUiState(
                onClick = {
                    otherStatesFlow.update { it.copy(navigateTo = news.id) }
                }
            )
        }
    }.cachedIn(viewModelScope)

    private val otherStatesFlow = MutableStateFlow(OtherStates(isLoading = true))

    val state = combine(pagingDataFlow, otherStatesFlow) { pagingData, otherStates ->
        NewsListState(
            isLoading = otherStates.isLoading,
            pagingData = pagingData,
            error = otherStates.error,
            navigateTo = otherStates.navigateTo
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000L),
        initialValue = NewsListState(isLoading = true)
    )

    fun reloadNewsList() {
        otherStatesFlow.update { it.copy(isLoading = true) }
        repository.reloadNewsList()
    }

    fun navigationDone() {
        otherStatesFlow.update { it.copy(navigateTo = null) }
    }

    fun errorHandlingDone() {
        otherStatesFlow.update { it.copy(error = null) }
    }
}

data class NewsListState(
    val isLoading: Boolean = false,
    val pagingData: PagingData<NewsUiState> = PagingData.empty(),
    val error: Throwable? = null,
    val navigateTo: String? = null
)

private data class OtherStates(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val navigateTo: String? = null
)

