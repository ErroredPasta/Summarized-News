package com.example.summarizednews.news.presentation.screen.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.summarizednews.R
import com.example.summarizednews.core.presentation.collectLatestWhenStarted
import com.example.summarizednews.core.presentation.showToast
import com.example.summarizednews.databinding.FragmentNewsListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsListFragment : Fragment() {
    private val adapter = NewsListAdapter()
    private val newsListViewModel by viewModels<NewsListViewModel>()
    private val navController by lazy { findNavController() }
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentNewsListBinding.inflate(inflater, container, false).apply {
        newsListRecyclerView.adapter = adapter
        viewModel = newsListViewModel
        setLifecycleOwner { this@NewsListFragment.viewLifecycleOwner.lifecycle }
        newsListRefreshLayout.setOnRefreshListener { newsListViewModel.fetchNewsList() }
    }.also { binding ->
        recyclerView = binding.newsListRecyclerView

        newsListViewModel.state.collectLatestWhenStarted(lifecycleOwner = viewLifecycleOwner) { state ->
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
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView?.adapter = null
        recyclerView = null
    }
}