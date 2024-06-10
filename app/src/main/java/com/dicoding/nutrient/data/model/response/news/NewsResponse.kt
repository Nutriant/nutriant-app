package com.dicoding.nutrient.data.model.response.news

import com.google.gson.annotations.SerializedName

data class NewsResponse(

    @field:SerializedName("articles")
    val articles: List<ArticlesItem?>? = null,
)

data class ArticlesItem(

    @field:SerializedName("urlToImage")
    val urlToImage: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("content")
    val content: String? = null,

    @field:SerializedName("publishedAt")
    val publish: String? = null,

    @field:SerializedName("description")
    val description: String? = null,
)