package com.example.summarizednews.summary.data.dto.response


import com.google.gson.annotations.SerializedName

data class Usage(
    @SerializedName("completion_tokens")
    val completionTokens: Int, // 60
    @SerializedName("prompt_tokens")
    val promptTokens: Int, // 728
    @SerializedName("total_tokens")
    val totalTokens: Int // 788
)