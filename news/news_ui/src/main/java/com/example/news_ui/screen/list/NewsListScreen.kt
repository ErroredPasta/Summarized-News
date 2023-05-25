package com.example.news_ui.screen.list

import android.view.LayoutInflater
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsListScreen(
    viewModel: NewsListViewModel,
    onNewsItemClick: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val newsList = viewModel.pagingDataFlow.collectAsLazyPagingItems()
    val isLoading by remember {
        derivedStateOf {
            newsList.loadState.refresh == LoadState.Loading
        }
    }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = {
            newsList.refresh()
            viewModel.reloadNewsList()
        }
    )

    Box(modifier = modifier.pullRefresh(pullRefreshState)) {
        PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        NewsListScreenContent(
            newsList = newsList,
            onNewsItemClick = onNewsItemClick,
        )
    }

}

@Composable
private fun NewsListScreenContent(
    newsList: LazyPagingItems<News>,
    onNewsItemClick: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    when (newsList.loadState.refresh) {
        LoadState.Loading -> {
            AndroidView(factory = {
                LoadingShimmerLayoutBinding.inflate(LayoutInflater.from(it)).root.apply {
                    startShimmer()
                }
            },
                modifier = modifier.fillMaxWidth()
            )
        }

        is LoadState.NotLoading -> LazyColumn(modifier = modifier.fillMaxWidth()) {
            items(
                count = newsList.itemCount,
                key = newsList.itemKey { it.id },
            ) { index ->
                val item = newsList[index]?.toNewsUiState(onClick = onNewsItemClick) ?: return@items
                NewsItem(uiState = item)
            }
        }

        is LoadState.Error -> context.showToast(stringRes = R.string.error_occurred_while_getting_news)

        else -> Unit
    }
}

@Preview(showBackground = true)
@Composable
private fun NewsListScreenPreview() {
    SummarizedNewsTheme {
        NewsListScreenContent(
            newsList = previewNewsList.collectAsLazyPagingItems(),
            onNewsItemClick = {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}

private val previewNewsList = (1..10).map {
    News(
        id = it.toString(),
        title = it.toString(),
        writtenAt = it.toString(),
        section = it.toString(),
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