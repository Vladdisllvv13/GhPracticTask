package com.example.icerockrepoapp.view


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.icerockrepoapp.App
import com.example.icerockrepoapp.R
import com.example.icerockrepoapp.viewModel.AuthViewModel
import com.example.icerockrepoapp.databinding.FragmentAuthBinding
import com.example.icerockrepoapp.viewModel.factory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AuthFragment : Fragment() {

    lateinit var binding: FragmentAuthBinding

    private val viewModel: AuthViewModel by viewModels { factory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btSignIn.setOnClickListener {
            val token = binding.tiToken.text

            if (!hasInternetConnection(binding.root.context)) showError("No connection")
            else if (token.isNullOrEmpty()) binding.tiToken.error = "empty"
            else if (isTokenValid(token.toString())) viewModel.signIn(token.toString())
            else binding.tiToken.error = "invalid token"
        }

        viewModel.state.observe(viewLifecycleOwner, Observer{ state ->
            if(state is AuthViewModel.State.Loading){
                binding.tvSignIn.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            } else{
                binding.tvSignIn.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
            if (state is AuthViewModel.State.InvalidInput) binding.tiToken.error = state.reason

        })

        lifecycleScope.launch {
            viewModel.actions.collect { handleAction(it) }
        }
    }

    private fun isTokenValid(token:String): Boolean{
        return !token.contains(Regex("[^[a-zA-Z0-9_]*\$]"))
    }


    private fun showError(message: String){
        val builder = AlertDialog.Builder(binding.root.context)
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setPositiveButton("OK", null)
        builder.show()

    }

    private fun handleAction(action: AuthViewModel.Action){
        when(action){
            is AuthViewModel.Action.RouteToMain -> navigate()
            is AuthViewModel.Action.ShowError -> showError("Error data / error code")
        }
    }

    private fun navigate(){
        findNavController().navigate(R.id.action_authFragment_to_repositoriesListFragment)
    }

    private fun hasInternetConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnected ?: false
    }



}
