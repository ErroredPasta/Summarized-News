package com.example.summarizednews.news.data.dto.detail


import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("content")
    val content: Content,
    @SerializedName("status")
    val status: String, // ok
    @SerializedName("total")
    val total: Int, // 1
    @SerializedName("userTier")
    val userTier: String // developer
)