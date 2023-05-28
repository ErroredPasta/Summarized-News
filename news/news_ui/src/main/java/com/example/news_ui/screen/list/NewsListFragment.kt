package com.example.news_ui.screen.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.core_ui.compose.SummarizedNewsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsListFragment : Fragment() {
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ComposeView(context = requireContext()).apply {
        setContent {
            SummarizedNewsTheme {
                NewsListScreen(
                    onNewsItemClick = { id -> navigateToDetailScreen(newsId = id) },
                )
            }
        }
    }

    private fun navigateToDetailScreen(newsId: String) {
        navController.navigate(NewsListFragmentDirections.actionNewsListFragmentToNewsDetailFragment(
            newsId = newsId
        ))
    }
}