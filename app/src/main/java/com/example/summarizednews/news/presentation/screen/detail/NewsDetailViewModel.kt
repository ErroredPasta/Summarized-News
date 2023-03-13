package com.example.summarizednews.news.presentation.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.summarizednews.core.util.runCoroutineCatching
import com.example.summarizednews.news.domain.model.NewsDetail
import com.example.summarizednews.news.domain.repository.NewsRepository
import com.example.summarizednews.summary.domain.repository.SummaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val summaryRepository: SummaryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val newsId = requireNotNull(savedStateHandle.get<String>("news_id"))

    private val actionChannel = Channel<Action>(capacity = Channel.BUFFERED)

    val state = actionChannel.receiveAsFlow()
        .onStart {
            emit(Action.FetchNewsDetail)
        }.scan(NewsDetailState()) { state, action ->
            handleSideEffect(action) { handledAction -> reduceState(state, handledAction) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000L),
            initialValue = NewsDetailState()
        )

    fun errorHandlingDone() {
        viewModelScope.launch { actionChannel.send(Action.ErrorHandlingDone) }
    }

    private fun reduceState(state: NewsDetailState, action: Action): NewsDetailState {
        return when (action) {
            Action.FetchNewsDetail -> state.copy(isLoading = true)
            is Action.ShowNewsDetail -> state.copy(isLoading = false, data = action.newsDetail)
            is Action.FetchSummary -> state
            is Action.ShowSummary -> state.copy(summary = action.summary)
            is Action.HandleError -> state.copy(error = action.error)
            Action.ErrorHandlingDone -> state.copy(error = null)
        }
    }

    private suspend fun handleSideEffect(
        action: Action,
        next: suspend (Action) -> NewsDetailState
    ): NewsDetailState {
        return runCoroutineCatching {
            when (action) {
                Action.FetchNewsDetail -> {
                    val newsDetail = newsRepository.getNewsDetailById(id = newsId)
                    actionChannel.send(Action.FetchSummary(content = newsDetail.body))
                    next(Action.ShowNewsDetail(newsDetail = newsDetail))
                }

                is Action.FetchSummary -> {
                    val summary = summaryRepository.summarize(content = action.content)
                    next(Action.ShowSummary(summary = summary))
                }

                else -> next(action)
            }
        }.getOrElse { cause -> next(Action.HandleError(error = cause)) }
    }
}

private sealed interface Action {
    object FetchNewsDetail : Action
    data class ShowNewsDetail(val newsDetail: NewsDetail) : Action
    data class FetchSummary(val content: String) : Action
    data class ShowSummary(val summary: String) : Action
    data class HandleError(val error: Throwable) : Action
    object ErrorHandlingDone : Action
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