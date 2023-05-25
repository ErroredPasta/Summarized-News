package com.example.summarizednews.news.presentation.screen.list

import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.shimmer.ShimmerFrameLayout

@BindingAdapter("showShimmerWhenLoading")
fun showShimmerWhenLoading(view: ShimmerFrameLayout, state: LoadState?) {
    if (state == null) return

    if (state == LoadState.Loading) {
        view.startShimmer()
        view.isVisible = true
    } else {
        view.stopShimmer()
        view.isVisible = false
    }
}

@BindingAdapter("showLoading")
fun showLoading(view: SwipeRefreshLayout, state: LoadState?) {
    if (state == null) return
    view.isRefreshing = state == LoadState.Loading
}

@BindingAdapter("showWhenNotLoading")
fun showWhenNotLoading(view: RecyclerView, state: LoadState?) {
    if (state == null) return
    view.isVisible = state is LoadState.NotLoading
}