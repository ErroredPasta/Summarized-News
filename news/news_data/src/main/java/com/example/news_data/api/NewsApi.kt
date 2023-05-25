package com.example.news_data.api

import com.example.news_data.dto.detail.NewsDetailResponse
import com.example.news_data.dto.list.NewsListResponse
import com.example.news_data.secret.NEWS_API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApi {
    @GET("/search?q=news&type=article&order-by=newest&api-key=$NEWS_API_KEY")
    suspend fun getNewsList(
        @Query("page") page: Int = 1
    ): NewsListResponse

    @GET("/{id}?show-fields=body&api-key=$NEWS_API_KEY")
    suspend fun getNewsDetailById(
        @Path(value = "id", encoded = true) id: String
    ): NewsDetailResponse
}