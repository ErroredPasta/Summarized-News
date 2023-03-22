package com.example.news_ui.screen.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.core_ui.repeatOnLifecycleWhenStarted
import com.example.core_ui.showToast
import com.example.news_ui.R
import com.example.news_ui.databinding.FragmentNewsListBinding
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
        savedInstanceState: Bundle?
    ): View = FragmentNewsListBinding.inflate(inflater, container, false).apply {
        newsListRecyclerView.adapter = adapter
        viewModel = newsListViewModel
        lifecycleOwner = this@NewsListFragment.viewLifecycleOwner
        newsListRefreshLayout.setOnRefreshListener {
            newsListViewModel.reloadNewsList()
            adapter.refresh()
        }
    }.also { binding ->
        recyclerView = binding.newsListRecyclerView

        viewLifecycleOwner.repeatOnLifecycleWhenStarted {
            newsListViewModel.state.collectLatest { state ->
                if (state.isLoading) {
                    binding.newsListLoadingShimmer.startShimmer()
                } else {
                    binding.newsListLoadingShimmer.stopShimmer()
                }

                binding.newsListRefreshLayout.isRefreshing = state.isLoading

                state.error?.let {
                    showToast(
                        state.error.message ?: getString(R.string.error_occurred_while_getting_news)
                    )
                    newsListViewModel.errorHandlingDone()
                }

                state.navigateTo?.let { newsId ->
                    navController.navigate(
                        NewsListFragmentDirections.actionNewsListFragmentToNewsDetailFragment(
                            newsId = newsId
                        )
                    )
                    newsListViewModel.navigationDone()
                }
            }
        }

        viewLifecycleOwner.repeatOnLifecycleWhenStarted {
            newsListViewModel.pagingDataFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }.root

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerView?.adapter = null
        recyclerView = null
    }
}