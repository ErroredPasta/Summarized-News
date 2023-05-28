package com.example.news_ui.screen.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Surface
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.core_ui.compose.SummarizedNewsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ComposeView(context = requireContext()).apply {
        setContent {
            SummarizedNewsTheme {
                Surface {
                    NewsDetailScreen()
                }
            }
        }
    }
}