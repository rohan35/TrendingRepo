package com.raydevelopers.trendinggitrepos.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raydevelopers.trendinggitrepos.callbacks.NetworkService
import com.raydevelopers.trendinggitrepos.database.TrendingDao
import com.raydevelopers.trendinggitrepos.model.TrendingRepositoryListObject
import com.raydevelopers.trendinggitrepos.network.NetworkResponse
import com.raydevelopers.trendinggitrepos.network.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class AppRepository private constructor(private var mTrendingDao: TrendingDao) {

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppRepository? = null

        fun getInstance(mTrendingDao: TrendingDao): AppRepository {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            return AppRepository(mTrendingDao)
        }

        // this live data will return the success and failure cases of work manager
        val workManagerStateLiveData = MutableLiveData<Boolean>()
    }

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


    fun setWorkManagerState(state: Boolean) {
        GlobalScope.launch(Dispatchers.Main) {
            workManagerStateLiveData.value = state
        }

    }

    fun getWorkManagerStateLiveData(): LiveData<Boolean> {
        return workManagerStateLiveData
    }
    suspend fun insert(trendingList: List<TrendingRepositoryListObject>) {
        mTrendingDao.insert(trendingList)

    }

    suspend fun deleteAll() {
        withContext(Dispatchers.IO) { mTrendingDao.deleteAll() }
    }

    fun getStringn():String
    {
        return "HI"
    }
}