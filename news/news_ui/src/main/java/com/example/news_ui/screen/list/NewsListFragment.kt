package com.example.news_ui.screen.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.RecyclerView
import com.example.core_ui.repeatOnLifecycleWhenStarted
import com.example.core_ui.showToast
import com.example.news_ui.R
import com.example.news_ui.databinding.FragmentNewsListBinding
import com.example.news_ui.mapper.toNewsUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class NewsListFragment : Fragment() {
    private val adapter = NewsListAdapter()
    private val newsListViewModel by viewModels<NewsListViewModel>()
    private val navController by lazy { findNavController() }
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = FragmentNewsListBinding.inflate(inflater, container, false).apply {
        newsListRecyclerView.adapter = adapter
        lifecycleOwner = this@NewsListFragment.viewLifecycleOwner
        newsListRefreshLayout.setOnRefreshListener {
            newsListViewModel.reloadNewsList()
            adapter.refresh()
        }
    }.also { binding ->
        recyclerView = binding.newsListRecyclerView

        viewLifecycleOwner.repeatOnLifecycleWhenStarted {
            adapter.loadStateFlow.collectLatest { loadState ->
                when (val currentState = loadState.refresh) {
                    LoadState.Loading -> {
                        binding.newsListLoadingShimmer.run {
                            isVisible = true
                            startShimmer()
                        }
                        binding.newsListRefreshLayout.isRefreshing = true
                        binding.newsListRecyclerView.isVisible = false
                    }
                    is LoadState.NotLoading -> {
                        binding.newsListLoadingShimmer.run {
                            isVisible = false
                            stopShimmer()
                        }
                        binding.newsListRefreshLayout.isRefreshing = false
                        binding.newsListRecyclerView.isVisible = true
                    }
                    is LoadState.Error -> {
                        showToast(
                            message = currentState.error.message
                                ?: getString(R.string.error_occurred_while_getting_news)
                        )
                    }
                }
            }
        }

        viewLifecycleOwner.repeatOnLifecycleWhenStarted {
            newsListViewModel.pagingDataFlow.collectLatest { pagingData ->
                val newsUiState = pagingData.map { news ->
                    news.toNewsUiState(onClick = { navigateToDetailScreen(newsId = news.id) })
                }

                adapter.submitData(newsUiState)
            }
        }
    }.root

    private fun navigateToDetailScreen(newsId: String) {
        navController.navigate(NewsListFragmentDirections.actionNewsListFragmentToNewsDetailFragment(
            newsId = newsId
        ))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView?.adapter = null
        recyclerView = null
    }
}