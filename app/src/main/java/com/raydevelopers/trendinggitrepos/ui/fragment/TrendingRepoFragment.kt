package com.raydevelopers.trendinggitrepos.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.raydevelopers.trendinggitrepos.R
import com.raydevelopers.trendinggitrepos.adapter.RepoListRecyclerAdapter
import com.raydevelopers.trendinggitrepos.databinding.TrendingRepoFragmentBinding
import com.raydevelopers.trendinggitrepos.model.TrendingRepositoryListObject
import com.raydevelopers.trendinggitrepos.ui.viewmodel.TrendingRepoViewModel
import kotlinx.android.synthetic.main.trending_repo_error_state.view.*


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
        // observer the list retrieved from database
        observeDatabaseList()
        // observer work manager
        observerWorkManagerResponse()
        // onClick listener for swip to refresh
        mRepoFragmentBinding?.swipeRefresh?.setOnRefreshListener {
           viewModel.runWorkManagerTask()
        }

        mRepoFragmentBinding?.errorState?.retryButton?.setOnClickListener{
            mRepoFragmentBinding?.shimmerContainer?.startShimmer()
            viewModel.runWorkManagerTask()
        }

    }

    private fun observeDatabaseList() {
        viewModel.allTrendingRepositoryList.observe(viewLifecycleOwner, Observer { responseList ->
            if (responseList.isNullOrEmpty() && mRepoFragmentBinding?.swipeRefresh?.isRefreshing == false) {
                viewModel.runWorkManagerTask()
            } else if (!responseList.isNullOrEmpty()) {
                setUpRecyclerView(responseList)
                viewModel.setWorkerState(true)
                mRepoFragmentBinding?.swipeRefresh?.isRefreshing = false
            }


        })
    }

    private fun observerWorkManagerResponse() {
        viewModel.getAppRepositoryLiveData().observe(viewLifecycleOwner, Observer { state ->
            if (state != null) {
                if (!state) {
                    mRepoFragmentBinding?.swipeRefresh?.isRefreshing = false
                    mRepoFragmentBinding?.errorState?.visibility = View.VISIBLE
                    mRepoFragmentBinding?.shimmerContainer?.visibility = View.GONE
                    mRepoFragmentBinding?.recyclerView?.visibility = View.GONE
                }
                else
                {
                    mRepoFragmentBinding?.errorState?.visibility = View.GONE
                    mRepoFragmentBinding?.recyclerView?.visibility = View.VISIBLE
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
