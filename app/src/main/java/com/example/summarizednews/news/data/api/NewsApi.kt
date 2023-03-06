package com.example.summarizednews.news.data.api

import com.example.summarizednews.BuildConfig
import com.example.summarizednews.news.data.dto.detail.NewsDetailResponse
import com.example.summarizednews.news.data.dto.list.NewsListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApi {

    @GET("/search?q=news&type=article&order-by=newest&api-key=${BuildConfig.NEWS_API_KEY}")
    suspend fun getNewsList(
        @Query("page") page: Int = 1
    ): NewsListResponse

    @GET("/{id}?show-fields=body&api-key=${BuildConfig.NEWS_API_KEY}")
    suspend fun getNewsDetailById(
        @Path(value = "id", encoded = true) id: String
    ): NewsDetailResponse
}