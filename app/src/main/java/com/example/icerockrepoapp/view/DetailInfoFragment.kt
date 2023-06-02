package com.example.icerockrepoapp.view


import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import com.example.icerockrepoapp.R
import com.example.icerockrepoapp.viewModel.DetailInfoViewModel
import com.example.icerockrepoapp.databinding.FragmentDetailInfoBinding
import com.example.icerockrepoapp.view.RepositoriesListFragment.Companion.ARG_REPO_NAME
import com.example.icerockrepoapp.viewModel.factory
import org.commonmark.node.Node
import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer

class DetailInfoFragment : Fragment() {

    private lateinit var binding: FragmentDetailInfoBinding

    private val viewModel: DetailInfoViewModel by viewModels { factory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repositoryName = requireArguments().getString(ARG_REPO_NAME)!!

        if (hasInternetConnection(binding.root.context)) {
            viewModel.getRepositoryReadme(repositoryName)
        }
        else showNoInternetView(repositoryName)

        viewModel.state.observe(viewLifecycleOwner, Observer { state ->

            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                if(state is DetailInfoViewModel.State.Loading) binding.progressBar.visibility = View.VISIBLE else binding.progressBar.visibility = View.GONE
                if(state is DetailInfoViewModel.State.Error) showErrorView(repositoryName)
                if(state is DetailInfoViewModel.State.Loaded){
                    showMainView()
                    binding.tvRepoLink.text = state.githubRepo.url
                    binding.tvRepoLicense.text = state.githubRepo.license
                    binding.tvRepoStars.text = state.githubRepo.starsCount.toString()
                    binding.tvRepoForks.text = state.githubRepo.forksCount.toString()
                    binding.tvRepoWatchers.text = state.githubRepo.watchersCount.toString()
                    if(state.readme.isNotEmpty()) showReadMe(state.readme)
                }
                binding.detailsContainer.visibility = if (state is DetailInfoViewModel.State.Loaded) View.VISIBLE else View.GONE
            }
        })

        viewModel.readmeState.observe(viewLifecycleOwner, Observer{ readmeState ->
            if(readmeState is DetailInfoViewModel.ReadmeState.Loading) binding.progressBar.visibility = View.VISIBLE else binding.progressBar.visibility = View.GONE
            if(readmeState is DetailInfoViewModel.ReadmeState.Error) showErrorView(repositoryName)
            if(readmeState is DetailInfoViewModel.ReadmeState.Loaded || readmeState is DetailInfoViewModel.ReadmeState.Empty){
                viewModel.getRepository(repositoryName)
            }
        })
    }

    private fun showNoInternetView(repositoryName: String){
        binding.mainView.visibility = View.GONE
        binding.errorView.visibility = View.GONE
        binding.noInternetView.visibility = View.VISIBLE
        binding.btRetryConnect.setOnClickListener{
            if (hasInternetConnection(binding.root.context)){
                viewModel.getRepositoryReadme(repositoryName)
                showMainView()
            }
        }
    }

    private fun showErrorView(repositoryName: String){
        binding.mainView.visibility = View.GONE
        binding.noInternetView.visibility = View.GONE
        binding.errorView.visibility = View.VISIBLE
        binding.btRetry.setOnClickListener{
            if (hasInternetConnection(binding.root.context)){
                viewModel.getRepositoryReadme(repositoryName)
                showMainView()
            }
            else showNoInternetView(repositoryName)
        }
    }

    private fun showMainView(){
        binding.mainView.visibility = View.VISIBLE
        binding.noInternetView.visibility = View.GONE
        binding.errorView.visibility = View.GONE

    }

    private fun showReadMe(content: String){

        val parser: Parser = Parser.builder().build()
        val document: Node = parser.parse(content)
        val renderer = HtmlRenderer.builder().build()
        val html: String = renderer.render(document)

        binding.wvReadme.loadDataWithBaseURL(null, html, "text/html", "en_US", null)
    }

    private fun hasInternetConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnected ?: false
    }


}