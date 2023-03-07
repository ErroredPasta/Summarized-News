package com.example.summarizednews.summary.data.api

import com.example.summarizednews.BuildConfig
import com.example.summarizednews.summary.data.dto.request.SummaryRequest
import com.example.summarizednews.summary.data.dto.response.SummaryResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SummaryApi {
    @POST("/v1/completions")
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer ${BuildConfig.SUMMARIZER_API_KEY}"
    )
    suspend fun summarize(
        @Body summaryRequest: SummaryRequest
    ): SummaryResponse
}