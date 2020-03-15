package com.raydevelopers.trendinggitrepos.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.raydevelopers.trendinggitrepos.database.AppDatabase
import com.raydevelopers.trendinggitrepos.model.TrendingRepositoryListObject
import com.raydevelopers.trendinggitrepos.repo.AppRepository
import com.raydevelopers.trendinggitrepos.workers.TrendingRepoListWorker
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class TrendingRepoViewModel(application: Application) : AndroidViewModel(application) {

    val allTrendingRepositoryList: LiveData<List<TrendingRepositoryListObject>>
    private val repository: AppRepository
    private val workManager = WorkManager.getInstance(application)
    private val TAG_OUTPUT = "workTag"
    var refreshOnButtonClicked = false
    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val trendingDao = AppDatabase.getDatabase(application,viewModelScope).trendingDao()
        repository = AppRepository.getInstance(trendingDao)
        allTrendingRepositoryList = repository.allTrendingListLiveData
    }

    private fun createConstraints() = Constraints.Builder()
        // other values(NOT_REQUIRED, CONNECTED, NOT_ROAMING, METERED)
        .setRequiresBatteryNotLow(true)                 // if the battery is not low
        .setRequiresStorageNotLow(true)                 // if the storage is not low
        .build()

    private fun createWorkRequest(): PeriodicWorkRequest {
        val build = PeriodicWorkRequestBuilder<TrendingRepoListWorker>(
            2,
            TimeUnit.HOURS
        )  // setting period to 2 hours
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL,2,TimeUnit.HOURS)
            .setConstraints(createConstraints())
            .build()
        return build
    }

    fun runWorkManagerTask()
    {
        workManager.enqueueUniquePeriodicWork(TAG_OUTPUT,ExistingPeriodicWorkPolicy.REPLACE,createWorkRequest())
    }
    fun getAppRepositoryLiveData():LiveData<Boolean>
    {
        return repository.getWorkManagerStateLiveData()
    }
    fun setWorkerState(state:Boolean)
    {
        repository.setWorkManagerState(state)
    }
}
