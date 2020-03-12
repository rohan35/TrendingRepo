package com.raydevelopers.trendinggitrepos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raydevelopers.trendinggitrepos.BR
import com.raydevelopers.trendinggitrepos.databinding.TrendingRepoListItemBinding
import com.raydevelopers.trendinggitrepos.model.TrendingRepositoryListObject

class RepoListRecyclerAdapter(var repoList: List<TrendingRepositoryListObject>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mExpandedPosition = -1
    private var  previousExpandedPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TrendingRepoListItemBinding.inflate(inflater, parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return repoList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val isExpanded: Boolean = position == mExpandedPosition;
        (holder as ListViewHolder).bind(repoList[position])
        holder.binding.layoutGroup.visibility = if (isExpanded) View.VISIBLE else View.GONE
        holder.itemView.isActivated = isExpanded;
        if (isExpanded)
            previousExpandedPosition = position;
        holder.binding.rootLayout.setOnClickListener {
            mExpandedPosition = if (isExpanded) -1 else position
            notifyItemChanged(previousExpandedPosition)
            notifyItemChanged(position)
        }
    }
    inner class ListViewHolder(var binding: TrendingRepoListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(trendingRepositoryListObject: TrendingRepositoryListObject) {
            binding.setVariable(BR.trendingRepoModel,  trendingRepositoryListObject)
        }

    }
}