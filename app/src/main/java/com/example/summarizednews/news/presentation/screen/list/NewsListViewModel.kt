package com.example.summarizednews.news.presentation.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.summarizednews.core.util.runCoroutineCatching
import com.example.summarizednews.news.domain.repository.NewsRepository
import com.example.summarizednews.news.presentation.mapper.toNewsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {
    private val actionChannel = Channel<Action>(capacity = Channel.BUFFERED)

    val pagingDataFlow = repository.getNewsList().map { pagingData ->
        actionChannel.send(Action.HideLoading)

        pagingData.map { news ->
            news.toNewsUiState(
                onClick = {
                    viewModelScope.launch { actionChannel.send(Action.Navigate(id = news.id)) }
                }
            )
        }
    }.catch { cause ->
        actionChannel.send(Action.HandleError(error = cause))
    }.cachedIn(viewModelScope)

    val state = actionChannel.receiveAsFlow()
        .scan(NewsListState()) { state, action ->
            handleSideEffect(action) { handledAction -> reduceState(state, handledAction) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000L),
            initialValue = NewsListState()
        )

    fun reloadNewsList() {
        viewModelScope.launch { actionChannel.send(Action.ReloadNewsList) }
    }

    fun navigationDone() {
        viewModelScope.launch { actionChannel.send(Action.NavigationDone) }
    }

    fun errorHandlingDone() {
        viewModelScope.launch { actionChannel.send(Action.ErrorHandlingDone) }
    }

    private fun reduceState(state: NewsListState, action: Action): NewsListState {
        return when (action) {
            is Action.HideLoading -> state.copy(isLoading = false)
            is Action.Navigate -> state.copy(navigateTo = action.id)
            Action.NavigationDone -> state.copy(navigateTo = null)
            Action.ReloadNewsList -> state.copy(isLoading = true)
            is Action.HandleError -> state.copy(error = action.error)
            Action.ErrorHandlingDone -> state.copy(error = null)
        }
    }

    private suspend fun handleSideEffect(action: Action, next: suspend (Action) -> NewsListState): NewsListState {
        return runCoroutineCatching {
            when (action) {
                Action.ReloadNewsList -> {
                    repository.reloadNewsList()
                    next(action)
                }

                else -> next(action)
            }
        }.getOrElse { cause -> next(Action.HandleError(error = cause)) }
    }
}

private sealed interface Action {
    object HideLoading : Action
    object ReloadNewsList : Action
    data class Navigate(val id: String) : Action
    object NavigationDone : Action
    data class HandleError(val error: Throwable) : Action
    object ErrorHandlingDone : Action
}

data class NewsListState(
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val navigateTo: String? = null
)

