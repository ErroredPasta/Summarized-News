package com.example.summarizednews.summary.data.repository

import com.example.summarizednews.core.di.Dispatcher
import com.example.summarizednews.summary.data.api.SummaryApi
import com.example.summarizednews.summary.data.dto.request.SummaryRequest
import com.example.summarizednews.summary.domain.repository.SummaryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SummaryRepositoryImpl @Inject constructor(
    private val summaryApi: SummaryApi,
    @Dispatcher(Dispatcher.Type.IO) private val dispatcher: CoroutineDispatcher
) : SummaryRepository {
    override suspend fun summarize(content: String): String = withContext(dispatcher) {
        summaryApi.summarize(
            summaryRequest = SummaryRequest(prompt = content + FOOTER)
        ).choices.first().text
    }

    companion object {
        private const val FOOTER = "\n\nTl;dr"
    }
}