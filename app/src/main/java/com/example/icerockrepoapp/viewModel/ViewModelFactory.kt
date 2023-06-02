package com.example.icerockrepoapp.viewModel

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.icerockrepoapp.App

class ViewModelFactory(
    private val app: App
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when(modelClass){
            AuthViewModel::class.java ->{
                AuthViewModel(app.appRepository)
            }
            RepositoriesListViewModel::class.java ->{
                RepositoriesListViewModel(app.appRepository)
            }
            DetailInfoViewModel::class.java ->{
                DetailInfoViewModel(app.appRepository)
            }
            else ->{
                throw java.lang.IllegalStateException("Unknown view model class")
            }
        }
        return viewModel as T
    }
}
fun Fragment.factory() = ViewModelFactory(requireContext().applicationContext as App)