package com.raydevelopers.trendinggitrepos.ui.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.raydevelopers.trendinggitrepos.R
import com.raydevelopers.trendinggitrepos.ui.viewmodel.TrendingRepoViewModel

class TrendingRepoFragment : Fragment() {

    companion object {
        fun newInstance() = TrendingRepoFragment()
    }

    private lateinit var viewModel: TrendingRepoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.trending_repo_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TrendingRepoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
