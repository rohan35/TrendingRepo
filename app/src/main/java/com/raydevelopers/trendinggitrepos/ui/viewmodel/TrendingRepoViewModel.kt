package com.raydevelopers.trendinggitrepos.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.raydevelopers.trendinggitrepos.data.Constants
import com.raydevelopers.trendinggitrepos.database.AppDatabase
import com.raydevelopers.trendinggitrepos.model.TrendingRepositoryListObject
import com.raydevelopers.trendinggitrepos.network.NetworkResource
import com.raydevelopers.trendinggitrepos.repo.AppRepository
import kotlinx.coroutines.launch

class TrendingRepoViewModel(application: Application) : AndroidViewModel(application) {

    val allTrendingRepositoryList: LiveData<List<TrendingRepositoryListObject>>
    private val repository: AppRepository

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val trendingDao = AppDatabase.getDatabase(application,viewModelScope).trendingDao()
        repository = AppRepository(trendingDao)
        allTrendingRepositoryList = repository.allTrendingListLiveData
    }
    fun getTrendingList(): LiveData<Any> {
        val queryMap = HashMap<String, String>()
        return repository.getCall(
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

    fun insert(trendingRepositoryListObject: List<TrendingRepositoryListObject>) = viewModelScope.launch {
        repository.insert(trendingRepositoryListObject)
    }
}
