package com.raydevelopers.trendinggitrepos.workers

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.raydevelopers.trendinggitrepos.data.Constants
import com.raydevelopers.trendinggitrepos.database.AppDatabase
import com.raydevelopers.trendinggitrepos.model.TrendingRepositoryListObject
import com.raydevelopers.trendinggitrepos.network.NetworkResource
import com.raydevelopers.trendinggitrepos.repo.AppRepository
import com.raydevelopers.trendinggitrepos.utility.observeOnce
import kotlinx.coroutines.*

open class TrendingRepoListWorker(application: Context, params: WorkerParameters) :
    CoroutineWorker(application, params) {
    val allTrendingRepositoryList: LiveData<List<TrendingRepositoryListObject>>
    private val repository: AppRepository

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val trendingDao =
            AppDatabase.getDatabase(application, CoroutineScope(Dispatchers.Default)).trendingDao()
        repository = AppRepository.getInstance(trendingDao)
        allTrendingRepositoryList = repository.allTrendingListLiveData
    }

    override suspend fun doWork(): Result = coroutineScope {
        var result: Result = Result.failure()
        // launchng it in global scope as observer should in main thread
        val job = async {
            GlobalScope.launch(Dispatchers.Main) {
                getTrendingList().observeOnce(Observer { response ->
                    response?.let {
                        CoroutineScope(Dispatchers.IO).launch {
                            val trendingRepoList = processTrendingList(it)
                            result = if (!trendingRepoList.isNullOrEmpty()) {
                                repository.deleteAll()
                                repository.insert(trendingRepoList)
                                repository.setWorkManagerState(true)
                                Result.success()
                            } else {
                                repository.setWorkManagerState(false)
                                Result.failure()
                            }


                        }

                    }

                }
                )
            }
        }
        job.await()
        Log.i("Hey u entered doWork()","doWork((last)")
        return@coroutineScope result
    }

    fun processTrendingList(rawResponse: Any): List<TrendingRepositoryListObject>? {
        val response = rawResponse as NetworkResource<Array<TrendingRepositoryListObject>>
        when (response.status) {
            NetworkResource.Status.SUCCESS -> {
                return (response.data)?.toList()
            }
            NetworkResource.Status.ERROR -> {
                return null
            }
        }
        return null
    }


    fun getTrendingList(): LiveData<Any> {
        val queryMap = HashMap<String, String>()
        return repository.getCall(
            Constants.trendingRepoGetUrl,
            queryMap,
            Array<TrendingRepositoryListObject>::class.java
        )
    }
}