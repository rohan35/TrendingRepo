package com.raydevelopers.trendinggitrepos.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.raydevelopers.trendinggitrepos.data.Constants
import com.raydevelopers.trendinggitrepos.model.TrendingRepositoryListObject
import com.raydevelopers.trendinggitrepos.network.NetworkResource
import com.raydevelopers.trendinggitrepos.repo.AppRepository

class TrendingRepoViewModel : ViewModel() {

    fun getTrendingList(): LiveData<Any> {
        val queryMap = HashMap<String, String>()
        return AppRepository.getCall(
            Constants.trendingRepoGetUrl,
            queryMap,
            Array<TrendingRepositoryListObject>::class.java
        )
    }

    fun processTrendingList(rawResponse:Any):List<TrendingRepositoryListObject>?
    {
        val response = rawResponse as NetworkResource<Array<TrendingRepositoryListObject>>
        when (response.status) {
            NetworkResource.Status.SUCCESS -> {
                return (response.data)?.toList()
            }
            NetworkResource.Status.ERROR -> {

            }
        }
        return null
    }
}
