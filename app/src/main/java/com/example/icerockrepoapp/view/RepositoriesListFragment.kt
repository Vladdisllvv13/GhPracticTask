package com.example.icerockrepoapp.view

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.icerockrepoapp.R
import com.example.icerockrepoapp.viewModel.RepositoriesListViewModel
import com.example.icerockrepoapp.databinding.FragmentRepositoriesListBinding
import com.example.icerockrepoapp.model.entity.Repo
import com.example.icerockrepoapp.viewModel.AuthViewModel
import com.example.icerockrepoapp.viewModel.ViewModelFactory
import com.example.icerockrepoapp.view.adapter.ItemClickListener
import com.example.icerockrepoapp.view.adapter.RepositoryAdapter
import com.example.icerockrepoapp.viewModel.factory

class RepositoriesListFragment : Fragment() {


    lateinit var binding: FragmentRepositoriesListBinding
    private lateinit var adapter: RepositoryAdapter


    private val viewModel: RepositoriesListViewModel by viewModels { factory() }

    companion object {
        const val ARG_REPO_NAME = "repositoryName"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepositoriesListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (hasInternetConnection(binding.root.context)) {
            viewModel.getRepositories()
        }
        else showNoInternetView()

        adapter = RepositoryAdapter(object : ItemClickListener{
            override fun onItemClick(repo: Repo) {
                findNavController().navigate(R.id.action_repositoriesListFragment_to_detailInfoFragment, bundleOf(ARG_REPO_NAME to repo.name))
            }
        })

        binding.rcView.layoutManager = LinearLayoutManager(binding.rcView.context)
        binding.rcView.adapter = adapter

        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if (state is RepositoriesListViewModel.State.Loading) binding.progressBar.visibility =
                    View.VISIBLE else binding.progressBar.visibility = View.GONE
                if (state is RepositoriesListViewModel.State.Error) showErrorView()
                if (state is RepositoriesListViewModel.State.Loaded){
                    adapter.repositories = state.repos
                    showMainView()
                }
            }
        }
        )
    }

    private fun showNoInternetView(){
        binding.mainView.visibility = View.GONE
        binding.errorView.visibility = View.GONE
        binding.noInternetView.visibility = View.VISIBLE
        binding.btRetryConnect.setOnClickListener{
            if (hasInternetConnection(binding.root.context)){
                viewModel.getRepositories()
                showMainView()
            }
        }
    }

    private fun showErrorView(){
        binding.mainView.visibility = View.GONE
        binding.noInternetView.visibility = View.GONE
        binding.errorView.visibility = View.VISIBLE
        binding.btRetry.setOnClickListener{
            if (hasInternetConnection(binding.root.context)){
                viewModel.getRepositories()
                showMainView()
            }
            else showNoInternetView()
        }
    }

    private fun showMainView(){
        binding.mainView.visibility = View.VISIBLE
        binding.noInternetView.visibility = View.GONE
        binding.errorView.visibility = View.GONE

    }


    private fun hasInternetConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnected ?: false
    }
}