package com.example.icerockrepoapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.icerockrepoapp.R
import com.example.icerockrepoapp.databinding.ItemRepositoryBinding
import com.example.icerockrepoapp.model.entity.Repo

interface ItemClickListener {
    fun onItemClick(repo: Repo)
}

class RepositoryAdapter(
    private val actionListener: ItemClickListener
): RecyclerView.Adapter<RepositoryAdapter.Holder>(), View.OnClickListener {

    var repositories: List<Repo> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun onClick(v: View) {
        val repo = v.tag as Repo
        actionListener.onItemClick(repo)
    }

    class Holder(val binding: ItemRepositoryBinding): RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRepositoryBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val repository = repositories[position]

        with(holder.binding){
            holder.itemView.tag = repository
            tvRepositoryName.text = repository.name
            tvLanguage.text = repository.language
            tvReadMe.text = repository.readme
        }
    }

    override fun getItemCount() = repositories.size
}