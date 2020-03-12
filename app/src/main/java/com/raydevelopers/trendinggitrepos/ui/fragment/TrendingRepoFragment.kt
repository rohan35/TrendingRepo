package com.raydevelopers.trendinggitrepos.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.raydevelopers.trendinggitrepos.R
import com.raydevelopers.trendinggitrepos.adapter.RepoListRecyclerAdapter
import com.raydevelopers.trendinggitrepos.databinding.TrendingRepoFragmentBinding
import com.raydevelopers.trendinggitrepos.model.TrendingRepositoryListObject
import com.raydevelopers.trendinggitrepos.ui.viewmodel.TrendingRepoViewModel
import androidx.recyclerview.widget.DividerItemDecoration



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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TrendingRepoViewModel::class.java)
        mRepoFragmentBinding?.shimmerContainer?.startShimmer()
        viewModel.getTrendingList().observe(viewLifecycleOwner, Observer { response ->
            response?.let {
                val trendingRepoList = viewModel.processTrendingList(it)
                trendingRepoList?.let {
                    setUpRecyclerView(trendingRepoList)
                }

            }
        })

    }

    private fun setUpRecyclerView(trendingRepoList: List<TrendingRepositoryListObject>)
    {
        recyclerAdapter = RepoListRecyclerAdapter(trendingRepoList)
        mRepoFragmentBinding?.recyclerView?.layoutManager  = LinearLayoutManager(this.context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        mRepoFragmentBinding?.recyclerView?.addItemDecoration(decoration)
        mRepoFragmentBinding?.recyclerView?.adapter = recyclerAdapter
        mRepoFragmentBinding?.shimmerContainer?.stopShimmer()
        mRepoFragmentBinding?.shimmerContainer?.visibility = View.GONE
    }

}
