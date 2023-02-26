package com.example.summerizednews.news.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.summerizednews.R
import com.example.summerizednews.databinding.FragmentNewsListBinding
import com.example.summerizednews.news.core.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsListFragment : BaseFragment<FragmentNewsListBinding>() {
    private val adapter = NewsListAdapter()
    private val viewModel by viewModels<NewsListViewModel>()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentNewsListBinding = FragmentNewsListBinding.inflate(inflater, container, false).apply {
        newsListRecyclerView.adapter = adapter
    }.also {
        viewModel.state.collectWhenStarted { state ->
            if (state.isLoading) {
                binding.newsListLoadingShimmer.startShimmer()
            } else {
                binding.newsListLoadingShimmer.stopShimmer()
            }

            adapter.submitList(state.data)

            state.error?.let {
                showToast(
                    state.error.message ?: getString(R.string.errored_occured_while_getting_news)
                )
            }
        }
    }
}