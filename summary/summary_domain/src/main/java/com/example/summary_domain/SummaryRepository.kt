package com.example.summary_domain

interface SummaryRepository {
    suspend fun summarize(content: String): String
}