package com.example.summarizednews.summary.data.dto.response


import com.google.gson.annotations.SerializedName

data class SummaryResponse(
    @SerializedName("choices")
    val choices: List<Choice>,
    @SerializedName("created")
    val created: Int, // 1678130079
    @SerializedName("id")
    val id: String, // cmpl-6rAa74HUpTqHKSiQiUssPdQoG1tKD
    @SerializedName("model")
    val model: String, // text-davinci-003
    @SerializedName("object")
    val objectX: String, // text_completion
    @SerializedName("usage")
    val usage: Usage
)