package com.example.summarizednews.summary.domain.repository

interface SummaryRepository {
    suspend fun summarize(content: String): String
}