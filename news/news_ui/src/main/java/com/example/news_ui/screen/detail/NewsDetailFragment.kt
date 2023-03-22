package com.example.news_ui.screen.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.core_ui.repeatOnLifecycleWhenStarted
import com.example.core_ui.showToast
import com.example.news_ui.R
import com.example.news_ui.databinding.FragmentNewsDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class NewsDetailFragment : Fragment() {
    private val viewModel by viewModels<NewsDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentNewsDetailBinding.inflate(inflater, container, false).apply {
        viewModel = this@NewsDetailFragment.viewModel
        lifecycleOwner = this@NewsDetailFragment.viewLifecycleOwner
    }.also {
        viewLifecycleOwner.repeatOnLifecycleWhenStarted {
            viewModel.state.collectLatest { state ->
                state.error?.let {
                    showToast(it.message ?: getString(R.string.error_occurred_while_getting_news))
                    viewModel.errorHandlingDone()
                }
            }
        }
    }.root
}