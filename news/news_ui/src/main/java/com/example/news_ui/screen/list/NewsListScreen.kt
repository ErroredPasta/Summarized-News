package com.example.news_ui.screen.list

import android.view.LayoutInflater
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.core_ui.compose.SummarizedNewsTheme
import com.example.core_ui.showToast
import com.example.news_domain.model.News
import com.example.news_ui.R
import com.example.news_ui.databinding.LoadingShimmerLayoutBinding
import com.example.news_ui.mapper.toNewsUiState
import kotlinx.coroutines.flow.flowOf

@Composable
fun NewsListScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsListViewModel = hiltViewModel(),
    onNewsItemClick: (id: String) -> Unit,
) {
    val newsList = viewModel.pagingDataFlow.collectAsLazyPagingItems()

    NewsListScreenContent(
        newsList = newsList,
        onNewsItemClick = onNewsItemClick,
        onRefresh = {
            newsList.refresh()
            viewModel.reloadNewsList()
        },
        modifier = modifier
    )
}

@Composable
private fun NewsListScreenContent(
    modifier: Modifier = Modifier,
    newsList: LazyPagingItems<News>,
    onNewsItemClick: (id: String) -> Unit,
    onRefresh: () -> Unit,
) {
    val context = LocalContext.current

    val isLoading by remember {
        derivedStateOf {
            newsList.loadState.refresh == LoadState.Loading
        }
    }

    PullRefreshLayout(
        isLoading = isLoading,
        onRefresh = onRefresh,
        modifier = modifier
    ) {
        when (val currentState = newsList.loadState.refresh) {
            LoadState.Loading -> {
                AndroidView(
                    factory = {
                        LoadingShimmerLayoutBinding.inflate(LayoutInflater.from(it)).root
                    },
                    update = { loadingShimmer -> loadingShimmer.startShimmer() },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            is LoadState.NotLoading -> LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(
                    count = newsList.itemCount,
                    key = newsList.itemKey { it.id },
                ) { index ->
                    val item =
                        newsList[index]?.toNewsUiState(onClick = onNewsItemClick) ?: return@items
                    NewsItem(uiState = item)
                }
            }

            is LoadState.Error -> {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.matchParentSize()
                ) {
                    Text(text = stringResource(R.string.please_try_again_later))
                    TextButton(onClick = onRefresh) {
                        Text(text = stringResource(R.string.retry))
                    }
                }
                context.showToast(
                    message = currentState.error.message
                        ?: stringResource(id = R.string.error_occurred_while_getting_news)
                )
            }

            else -> Unit
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PullRefreshLayout(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    onRefresh: () -> Unit,
    content: @Composable BoxScope.() -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = onRefresh
    )

    Box(
        modifier = modifier.pullRefresh(pullRefreshState),
    ) {
        content()
        PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NewsListScreenContentLoadingPreview() {
    SummarizedNewsTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            NewsListScreenContent(
                newsList = previewLoadingNewsList.collectAsLazyPagingItems(),
                onNewsItemClick = {},
                onRefresh = {}
            )
        }
    }
}

private val previewLoadingNewsList = flowOf(PagingData.from(
    data = emptyList<News>(),
    sourceLoadStates = LoadStates(
        refresh = LoadState.Loading,
        prepend = LoadState.Loading,
        append = LoadState.Loading,
    )
))

@Preview(showBackground = true)
@Composable
private fun NewsListScreenSuccessPreview() {
    SummarizedNewsTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            NewsListScreenContent(
                newsList = previewSuccessNewsList.collectAsLazyPagingItems(),
                onNewsItemClick = {},
                onRefresh = {},
            )
        }
    }
}

private val previewSuccessNewsList = (1..20).map {
    News(
        id = it.toString(),
        title = "Title $it",
        writtenAt = it.toString(),
        section = "Media",
    )
}.let {
    flowOf(PagingData.from(
        data = it,
        sourceLoadStates = LoadStates(
            refresh = LoadState.NotLoading(false),
            prepend = LoadState.NotLoading(false),
            append = LoadState.NotLoading(false),
        )
    ))
}

@Preview(showBackground = true)
@Composable
private fun NewsListScreenContentErrorPreview() {
    SummarizedNewsTheme {
        Surface {
            NewsListScreenContent(
                newsList = previewErrorNewsList.collectAsLazyPagingItems(),
                onNewsItemClick = {},
                onRefresh = {}
            )
        }
    }
}

private val previewErrorNewsList = flowOf(PagingData.from(
    data = emptyList<News>(),
    sourceLoadStates = LoadStates(
        refresh = LoadState.Error(error = Exception("Error occurred")),
        prepend = LoadState.Error(error = Exception("Error occurred")),
        append = LoadState.Error(error = Exception("Error occurred")),
    )
))