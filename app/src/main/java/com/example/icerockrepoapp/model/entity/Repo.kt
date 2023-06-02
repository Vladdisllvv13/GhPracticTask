package com.example.icerockrepoapp.model.entity

import com.google.gson.annotations.SerializedName

data class Repo(
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val readme: String,
    @SerializedName("language")
    val language: String
)