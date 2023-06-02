package com.example.icerockrepoapp.model.core

import com.example.icerockrepoapp.model.entity.ReadMe
import com.example.icerockrepoapp.model.entity.Repo
import com.example.icerockrepoapp.model.entity.RepoDetails
import com.example.icerockrepoapp.model.entity.UserInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {

    @GET("users/{userName}/repos")
    suspend fun getRepositories(
        @Path("userName") user: String,
        @Header("authorization") token: String
    ): Response<List<Repo>>

    @GET("repos/{userName}/{nameRepository}")
    suspend fun getRepository(
        @Path("userName") user: String,
        @Path("nameRepository") nameRepository: String,
        @Header("Authorization") token: String
    ): Response<RepoDetails>

    @GET("repos/{userName}/{nameRepository}/readme")
    suspend fun getRepositoryReadme(
        @Path("userName") user: String,
        @Path("nameRepository") nameRepository: String,
        @Header("Authorization") token: String
    ): Response<ReadMe>

    @GET("user")
    suspend fun signIn(
        @Header("Authorization") token: String
    ): Response<UserInfo>
}