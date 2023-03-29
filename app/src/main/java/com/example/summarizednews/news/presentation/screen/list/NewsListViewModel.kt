package com.example.summarizednews.news.presentation.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.summarizednews.news.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val repository: NewsRepository,
) : ViewModel() {
    val pagingDataFlow = repository.getNewsList().cachedIn(viewModelScope)

    fun reloadNewsList() {
        repository.reloadNewsList()
    }
}
