package com.example.icerockrepoapp.model.entity

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("login")
    val login: String
)