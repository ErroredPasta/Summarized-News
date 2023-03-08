package com.example.summarizednews.news.presentation.screen.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.summarizednews.R
import com.example.summarizednews.core.presentation.BaseFragment
import com.example.summarizednews.databinding.FragmentNewsDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailFragment : BaseFragment<FragmentNewsDetailBinding>() {
    private val viewModel by viewModels<NewsDetailViewModel>()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentNewsDetailBinding = FragmentNewsDetailBinding.inflate(inflater, container, false).apply {
        viewModel = this@NewsDetailFragment.viewModel
        lifecycleOwner = this@NewsDetailFragment.viewLifecycleOwner
    }.also {
        viewModel.state.collectWhenStarted { state ->
            state.error?.let {
                showToast(it.message ?: getString(R.string.error_occurred_while_getting_news))
                viewModel.errorHandlingDone()
            }
        }
    }
}