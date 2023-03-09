package com.example.summarizednews.news.presentation.screen.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.summarizednews.R
import com.example.summarizednews.core.presentation.BaseFragment
import com.example.summarizednews.databinding.FragmentNewsListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsListFragment : BaseFragment<FragmentNewsListBinding>() {
    private val adapter = NewsListAdapter()
    private val newsListViewModel by viewModels<NewsListViewModel>()
    private val navController by lazy { findNavController() }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentNewsListBinding = FragmentNewsListBinding.inflate(inflater, container, false).apply {
        newsListRecyclerView.adapter = adapter
        viewModel = newsListViewModel
        setLifecycleOwner { this@NewsListFragment.viewLifecycleOwner.lifecycle }
        newsListRefreshLayout.setOnRefreshListener { newsListViewModel.fetchNewsList() }
    }.also { binding ->
        newsListViewModel.state.collectWhenStarted { state ->
            if (state.isLoading) {
                binding.newsListLoadingShimmer.startShimmer()
            } else {
                binding.newsListLoadingShimmer.stopShimmer()
            }

            binding.newsListRefreshLayout.isRefreshing = state.isLoading

            adapter.submitList(state.data)

            state.error?.let {
                showToast(
                    state.error.message ?: getString(R.string.error_occurred_while_getting_news)
                )
                newsListViewModel.errorHandlingDone()
            }

            state.navigateTo?.let { newsId ->
                navController.navigate(
                    NewsListFragmentDirections.actionNewsListFragmentToNewsDetailFragment(newsId)
                )
                newsListViewModel.navigationDone()
            }
        }
    }
}