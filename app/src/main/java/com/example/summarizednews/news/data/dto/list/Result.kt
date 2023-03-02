package com.example.summarizednews.news.data.dto.list


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("apiUrl")
    val apiUrl: String, // https://content.guardianapis.com/media/2023/feb/10/news-corp-cuts-driven-by-murdoch-mission-to-prop-up-news-assets
    @SerializedName("id")
    val id: String, // media/2023/feb/10/news-corp-cuts-driven-by-murdoch-mission-to-prop-up-news-assets
    @SerializedName("isHosted")
    val isHosted: Boolean, // false
    @SerializedName("pillarId")
    val pillarId: String, // pillar/news
    @SerializedName("pillarName")
    val pillarName: String, // News
    @SerializedName("sectionId")
    val sectionId: String, // media
    @SerializedName("sectionName")
    val sectionName: String, // Media
    @SerializedName("type")
    val type: String, // article
    @SerializedName("webPublicationDate")
    val webPublicationDate: String, // 2023-02-10T16:37:18Z
    @SerializedName("webTitle")
    val webTitle: String, // News Corp cuts driven by Murdoch’s mission to prop up news assets
    @SerializedName("webUrl")
    val webUrl: String // https://www.theguardian.com/media/2023/feb/10/news-corp-cuts-driven-by-murdoch-mission-to-prop-up-news-assets
)