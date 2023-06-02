package com.example.icerockrepoapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.icerockrepoapp.model.entity.UserInfo
import com.example.icerockrepoapp.model.repository.AppRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class AuthViewModel (private val appRepository: AppRepository) : ViewModel() {

    private var job: Job? = null

    private val _errorMessage = MutableLiveData<String>()
    private val _exceptionHandler = CoroutineExceptionHandler { _, throwable -> _errorMessage.postValue(throwable.message) }

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state


    private val _actions: Channel<Action> = Channel(Channel.BUFFERED)
    val actions: Flow<Action> = _actions.receiveAsFlow()

    init {
        if (appRepository.isSignUpped()) signIn(appRepository.getToken())
    }

    sealed interface State {
        object Idle : State
        object Loading : State
        data class InvalidInput(val reason: String) : State
    }

    sealed interface Action {
        data class ShowError(val message: String) : Action
        object RouteToMain : Action
    }

    fun signIn(token: String){
        _state.postValue(State.Loading)

        job = viewModelScope.launch(Dispatchers.IO + _exceptionHandler) {
            val response = appRepository.signIn("token $token")
            if (!response.isSuccessful) onErrorSignIn("cannot Success", "Error: $_errorMessage")
            if (response.body() == null) onErrorSignIn("cannot Success", "Error: No user")
            else {
                appRepository.saveUser(response.body()!!.login)
                appRepository.saveToken(token)
                onSuccessSignIn()
            }
        }
    }


    private suspend fun onSuccessSignIn(){
        _actions.send(Action.RouteToMain)
        _state.postValue(State.Idle)
    }

    private suspend fun onErrorSignIn(message: String, reason: String){
        _actions.send(Action.ShowError(message))
        _state.postValue(State.InvalidInput(reason))
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}