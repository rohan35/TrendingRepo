package com.raydevelopers.trendinggitrepos.ui.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.raydevelopers.trendinggitrepos.R
import com.raydevelopers.trendinggitrepos.adapter.RepoListRecyclerAdapter
import com.raydevelopers.trendinggitrepos.databinding.TrendingRepoFragmentBinding
import com.raydevelopers.trendinggitrepos.model.TrendingRepositoryListObject
import com.raydevelopers.trendinggitrepos.ui.viewmodel.TrendingRepoViewModel

class TrendingRepoFragment : Fragment() {
    private var mRepoFragmentBinding:TrendingRepoFragmentBinding? = null
    companion object {
        fun newInstance() = TrendingRepoFragment()
    }

    private lateinit var viewModel: TrendingRepoViewModel
    private lateinit var recyclerAdapter: RepoListRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mRepoFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.trending_repo_fragment,container,false)
        return mRepoFragmentBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TrendingRepoViewModel::class.java)
        viewModel.getTrendingList().observe(viewLifecycleOwner, Observer {
            repoList->
            if(repoList!=null)
            {
                val trendingRepoList = repoList as ArrayList<TrendingRepositoryListObject>
                setUpRecyclerView(trendingRepoList)
            }
        })

    }

    private fun setUpRecyclerView(trendingRepoList: ArrayList<TrendingRepositoryListObject>)
    {
        recyclerAdapter = RepoListRecyclerAdapter(trendingRepoList)
        mRepoFragmentBinding?.recyclerView?.layoutManager  = LinearLayoutManager(this.context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        mRepoFragmentBinding?.recyclerView?.adapter = recyclerAdapter
    }

}
