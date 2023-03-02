package com.example.summarizednews.news.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.summarizednews.R
import com.example.summarizednews.news.core.presentation.BaseFragment
import com.example.summarizednews.databinding.FragmentNewsListBinding
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