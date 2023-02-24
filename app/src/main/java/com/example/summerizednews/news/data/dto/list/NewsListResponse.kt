package com.example.summerizednews.news.data.dto.list


import com.google.gson.annotations.SerializedName

data class NewsListResponse(
    @SerializedName("response")
    val response: Response
)