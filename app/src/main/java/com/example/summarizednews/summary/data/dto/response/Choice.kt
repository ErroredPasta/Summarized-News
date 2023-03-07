package com.example.summarizednews.summary.data.dto.response


import com.google.gson.annotations.SerializedName

data class Choice(
    @SerializedName("finish_reason")
    val finishReason: String, // length
    @SerializedName("index")
    val index: Int, // 0
    @SerializedName("logprobs")
    val logprobs: Any, // null
    @SerializedName("text")
    val text: String // : A 2022 study showed that 66% of young people get their news from social media sources and algorithms, which can lead to an abundance of polarised and conflicting views. As a geography teacher, I often ask my students about the sources they use for news, and I am deeply concerned about the quality
)