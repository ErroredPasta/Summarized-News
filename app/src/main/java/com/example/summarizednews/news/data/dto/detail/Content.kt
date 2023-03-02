package com.example.summarizednews.news.data.dto.detail


import com.google.gson.annotations.SerializedName

data class Content(
    @SerializedName("apiUrl")
    val apiUrl: String, // https://content.guardianapis.com/media/2023/feb/22/what-happens-if-teens-get-their-news-from-tiktok
    @SerializedName("fields")
    val fields: Fields,
    @SerializedName("id")
    val id: String, // media/2023/feb/22/what-happens-if-teens-get-their-news-from-tiktok
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
    val webPublicationDate: String, // 2023-02-22T18:07:00Z
    @SerializedName("webTitle")
    val webTitle: String, // What happens if teens get their news from TikTok? | Letter
    @SerializedName("webUrl")
    val webUrl: String // https://www.theguardian.com/media/2023/feb/22/what-happens-if-teens-get-their-news-from-tiktok
)