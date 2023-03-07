package com.example.summarizednews.summary.data.dto.request


import com.google.gson.annotations.SerializedName

data class SummaryRequest(
    @SerializedName("prompt")
    val prompt: String = ""
) {
    @SerializedName("frequency_penalty")
    val frequencyPenalty: Double = 0.0

    @SerializedName("max_tokens")
    val maxTokens: Int = 60

    @SerializedName("presence_penalty")
    val presencePenalty: Int = 1

    @SerializedName("model")
    val model: String = "text-davinci-003"

    @SerializedName("temperature")
    val temperature: Double = 0.7

    @SerializedName("top_p")
    val topP: Double = 1.0
}