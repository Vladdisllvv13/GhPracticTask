package com.example.icerockrepoapp.viewModel

import android.util.Base64
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.icerockrepoapp.model.entity.ReadMe
import com.example.icerockrepoapp.model.entity.Repo
import com.example.icerockrepoapp.model.entity.RepoDetails
import com.example.icerockrepoapp.model.repository.AppRepository
import kotlinx.coroutines.*


class DetailInfoViewModel(private val appRepository: AppRepository) : ViewModel() {

    private var job: Job? = null

    private val _errorMessage = MutableLiveData<String>()
    private val _exceptionHandler = CoroutineExceptionHandler { _, throwable -> _errorMessage.postValue(throwable.message) }

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private val _readmeState = MutableLiveData<ReadmeState>()
    val readmeState: LiveData<ReadmeState> = _readmeState

    sealed interface State {
        object Loading : State
        data class Error(val error: String) : State

        data class Loaded(
            val githubRepo: RepoDetails,
            val readme: String
        ) : State
    }

    sealed interface ReadmeState {
        object Loading : ReadmeState
        object Empty : ReadmeState
        data class Error(val error: String) : ReadmeState
        data class Loaded(val markdown: String) : ReadmeState
    }


    fun getRepository(repositoryName: String){
        _state.postValue(State.Loading)

        viewModelScope.launch (Dispatchers.IO + _exceptionHandler) {
            val response = appRepository.getRepository(repositoryName)
            if (!response.isSuccessful) _state.postValue(State.Error(response.message()))
            else if (_readmeState.value is ReadmeState.Loaded){
                _state.postValue(State.Loaded(response.body()!!, (_readmeState.value as ReadmeState.Loaded).markdown))
            }
            else _state.postValue(State.Loaded(response.body()!!, ""))
        }
    }

    fun getRepositoryReadme(repositoryName: String){
        _readmeState.postValue(ReadmeState.Loading)

        job = viewModelScope.async(Dispatchers.IO + _exceptionHandler){
            val response = appRepository.getRepositoryReadme(repositoryName)
            if (response.body() == null) _readmeState.postValue(ReadmeState.Empty)
            else if (!response.isSuccessful) _readmeState.postValue(ReadmeState.Error(response.errorBody().toString()))
            else {
                val decodedReadme = Base64.decode(response.body()!!.content, Base64.DEFAULT).toString(Charsets.UTF_8)
                _readmeState.postValue(ReadmeState.Loaded(decodedReadme))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}
