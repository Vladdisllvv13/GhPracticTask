package com.example.icerockrepoapp.model.entity

import com.google.gson.annotations.SerializedName

data class RepoDetails(
    @SerializedName("html_url")
    val url: String,
    @SerializedName("license")
    val license: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("stars_count")
    val starsCount: Int,
    @SerializedName("forks_count")
    val forksCount: Int,
    @SerializedName("watchers_count")
    val watchersCount: Int
)
