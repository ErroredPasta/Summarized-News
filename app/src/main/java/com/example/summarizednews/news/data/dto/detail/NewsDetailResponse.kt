package com.example.summarizednews.news.data.dto.detail


import com.google.gson.annotations.SerializedName

data class NewsDetailResponse(
    @SerializedName("response")
    val response: Response
)