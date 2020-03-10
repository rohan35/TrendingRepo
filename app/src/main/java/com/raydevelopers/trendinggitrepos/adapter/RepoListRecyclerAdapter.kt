package com.raydevelopers.trendinggitrepos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raydevelopers.trendinggitrepos.BR
import com.raydevelopers.trendinggitrepos.databinding.TrendingRepoListItemBinding
import com.raydevelopers.trendinggitrepos.model.TrendingRepositoryListObject

class RepoListRecyclerAdapter(var repoList: ArrayList<TrendingRepositoryListObject>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TrendingRepoListItemBinding.inflate(inflater, parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return repoList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ListViewHolder).bind(repoList[position])
    }
    inner class ListViewHolder(var binding: TrendingRepoListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(trendingRepositoryListObject: TrendingRepositoryListObject) {
            binding.setVariable(BR.trendingRepoModel,  trendingRepositoryListObject)
        }
    }
}