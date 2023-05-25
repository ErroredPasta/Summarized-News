package com.example.summary_data.api

import com.example.summary_data.dto.request.SummaryRequest
import com.example.summary_data.dto.response.SummaryResponse
import com.example.summary_data.secret.SUMMARIZER_API_KEY
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SummaryApi {
    @POST("/v1/completions")
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer $SUMMARIZER_API_KEY"
    )
    suspend fun summarize(
        @Body summaryRequest: SummaryRequest
    ): SummaryResponse
}