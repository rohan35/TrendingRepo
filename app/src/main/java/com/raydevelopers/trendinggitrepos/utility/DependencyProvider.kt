package com.raydevelopers.trendinggitrepos.utility

import android.app.Application
import androidx.work.WorkManager
import com.raydevelopers.trendinggitrepos.database.AppDatabase
import com.raydevelopers.trendinggitrepos.database.TrendingDao
import com.raydevelopers.trendinggitrepos.repo.AppRepository
import com.raydevelopers.trendinggitrepos.ui.viewmodel.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

object DependencyProvider {
    /**
     *
     */
    fun provideViewModelFactory(
        application: Application
    ): ViewModelFactory {
        return ViewModelFactory(getRepository(application), getWorkManager(application))
    }


    /**
     *
     */
    private fun getTrendingDaoObject(application: Application): TrendingDao {
        return AppDatabase.getDatabase(application, CoroutineScope(Dispatchers.IO)).trendingDao()
    }

    /**
     *
     */
    private fun getRepository(application: Application): AppRepository{
        return AppRepository.getInstance(getTrendingDaoObject(application))
    }

    private fun getWorkManager(application: Application):WorkManager
    {
        return WorkManager.getInstance(application)
    }



}