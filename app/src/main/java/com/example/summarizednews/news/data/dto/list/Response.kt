package com.example.summarizednews.news.data.dto.list


import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("currentPage")
    val currentPage: Int, // 1
    @SerializedName("orderBy")
    val orderBy: String, // relevance
    @SerializedName("pageSize")
    val pageSize: Int, // 10
    @SerializedName("pages")
    val pages: Int, // 43116
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("startIndex")
    val startIndex: Int, // 1
    @SerializedName("status")
    val status: String, // ok
    @SerializedName("total")
    val total: Int, // 431155
    @SerializedName("userTier")
    val userTier: String // developer
)