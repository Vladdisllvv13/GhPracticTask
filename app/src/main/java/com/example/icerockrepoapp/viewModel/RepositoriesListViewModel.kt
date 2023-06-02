package com.example.icerockrepoapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.icerockrepoapp.model.entity.Repo
import com.example.icerockrepoapp.model.repository.AppRepository
import kotlinx.coroutines.*
import retrofit2.Response

class RepositoriesListViewModel(private val appRepository: AppRepository) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private val _errorMessage = MutableLiveData<String>()
    private val _exceptionHandler = CoroutineExceptionHandler { _, throwable -> _errorMessage.postValue(throwable.message) }

    sealed interface State {
        object Loading : State
        data class Loaded(val repos: List<Repo>) : State
        data class Error(val error: String) : State
        object Empty : State
    }


    private var job: Job? = null


    fun getRepositories(){
        _state.postValue(State.Loading)

        job = viewModelScope.launch(Dispatchers.IO + _exceptionHandler) {
            val response = appRepository.getRepositories()
            if (response.isSuccessful){
                if (response.body() != null) _state.postValue(State.Loaded(response.body()!!))
                else _state.postValue(State.Empty)
            }
            else _state.postValue(State.Error("Error ${response.errorBody().toString()}"))
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}

