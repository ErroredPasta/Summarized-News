package com.example.summerizednews.news.data.api

import com.example.summerizednews.BuildConfig
import com.example.summerizednews.news.data.dto.detail.NewsDetailResponse
import com.example.summerizednews.news.data.dto.list.NewsListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApi {

    @GET("/search?q=news&api-key=${BuildConfig.NEWS_API_KEY}")
    suspend fun getNewsList(
        @Query("page") page: Int = 1
    ): NewsListResponse

    @GET("/{id}?show-fields=body&api-key=${BuildConfig.NEWS_API_KEY}")
    suspend fun getNewsDetailById(
        @Path("id") id: String
    ): NewsDetailResponse
}