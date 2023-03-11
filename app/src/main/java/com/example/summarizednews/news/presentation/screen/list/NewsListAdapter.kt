package com.example.summarizednews.news.presentation.screen.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.summarizednews.databinding.ViewHolderNewsItemBinding

class NewsListAdapter : PagingDataAdapter<NewsUiState, NewsItemViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        return NewsItemViewHolder(
            ViewHolderNewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}

private val diffUtil = object : DiffUtil.ItemCallback<NewsUiState>() {
    override fun areItemsTheSame(oldItem: NewsUiState, newItem: NewsUiState): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NewsUiState, newItem: NewsUiState): Boolean {
        return oldItem == newItem
    }

}

class NewsItemViewHolder(
    private val binding: ViewHolderNewsItemBinding
) : ViewHolder(binding.root) {
    fun bind(uiState: NewsUiState) {
        binding.uiState = uiState
    }
}