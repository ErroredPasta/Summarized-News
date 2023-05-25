package com.example.summary_data.repository

import com.example.core_util.Dispatcher
import com.example.summary_data.api.SummaryApi
import com.example.summary_data.dto.request.SummaryRequest
import com.example.summary_domain.SummaryRepository
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