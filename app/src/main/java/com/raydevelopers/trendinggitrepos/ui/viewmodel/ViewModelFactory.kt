package com.raydevelopers.trendinggitrepos.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager
import com.raydevelopers.trendinggitrepos.repo.AppRepository


class ViewModelFactory(
    private val appRepository: AppRepository,
    private val workManager: WorkManager
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TrendingRepoViewModel(appRepository,workManager) as T
    }
}