package com.example.summarizednews.news.presentation.screen.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.RecyclerView
import com.example.summarizednews.R
import com.example.summarizednews.core.presentation.repeatOnLifecycleWhenStarted
import com.example.summarizednews.core.presentation.showToast
import com.example.summarizednews.databinding.FragmentNewsListBinding
import com.example.summarizednews.news.presentation.mapper.toNewsUiState
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
                val currentState = loadState.refresh

                binding.loadState = currentState

                if (currentState is LoadState.Error) {
                    showToast(
                        message = currentState.error.message
                            ?: getString(R.string.error_occurred_while_getting_news)
                    )
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