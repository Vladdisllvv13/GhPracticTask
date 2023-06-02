package com.example.icerockrepoapp.model.repository

import android.content.Context
import android.text.Editable
import com.example.icerockrepoapp.model.core.RetrofitInstance
import com.example.icerockrepoapp.model.entity.ReadMe
import com.example.icerockrepoapp.model.entity.Repo
import com.example.icerockrepoapp.model.entity.RepoDetails
import com.example.icerockrepoapp.model.entity.UserInfo
import com.example.icerockrepoapp.model.storage.KeyValueStorage
import retrofit2.Response

class AppRepository(context: Context) {

    private val keyValueStorage = KeyValueStorage(context)

    suspend fun getRepositories(): Response<List<Repo>> {
        return RetrofitInstance.api.getRepositories(
            user = keyValueStorage.user!!,
            token = keyValueStorage.token!!
        )
    }

    suspend fun getRepository(repositoryName: String): Response<RepoDetails> {
        return RetrofitInstance.api.getRepository(
            user = keyValueStorage.user!!,
            nameRepository = repositoryName,
            token = keyValueStorage.token!!
        )
    }

    suspend fun getRepositoryReadme(repositoryName: String): Response<ReadMe> {
        return RetrofitInstance.api.getRepositoryReadme(
            user = keyValueStorage.user!!,
            nameRepository = repositoryName,
            token = keyValueStorage.token!!
        )
    }

    suspend fun signIn(personalToken: String): Response<UserInfo> {

        return RetrofitInstance.api.signIn(
            token = personalToken
        )
    }

    fun saveUser(user: String){
        keyValueStorage.user = user
    }

    fun saveToken(token: String) {
        keyValueStorage.token = token
    }

    fun getToken(): String{
        return keyValueStorage.token!!
    }

    fun isSignUpped(): Boolean{
        return keyValueStorage.token != null && keyValueStorage.user != null
    }
}