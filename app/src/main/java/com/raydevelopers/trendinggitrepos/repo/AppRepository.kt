package com.raydevelopers.trendinggitrepos.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raydevelopers.trendinggitrepos.callbacks.NetworkService
import com.raydevelopers.trendinggitrepos.database.TrendingDao
import com.raydevelopers.trendinggitrepos.model.TrendingRepositoryListObject
import com.raydevelopers.trendinggitrepos.network.NetworkResponse
import com.raydevelopers.trendinggitrepos.network.RetrofitService

class AppRepository(private val mTrendingDao: TrendingDao) {
        fun getCall(
            subUrl: String,
            queryMap: Map<String, String>,
            objectClass: Class<*>
        ): LiveData<Any> {
            var response = MutableLiveData<Any>()
            val call =
                (RetrofitService.createService(NetworkService::class.java) as NetworkService).getRequest(
                    subUrl,
                    queryMap
                );
            val networkResponse = NetworkResponse(response, objectClass)
            call.enqueue(networkResponse)
            return response
        }

        // Room executes all queries on a separate thread.
        // Observed LiveData will notify the observer when the data has changed.
        val allTrendingListLiveData: LiveData<List<TrendingRepositoryListObject>> =
            mTrendingDao.getTrendingRepoList()

        suspend fun insert(trendingList: List<TrendingRepositoryListObject>) {
                mTrendingDao.insert(trendingList)

        }
    }