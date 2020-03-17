package com.raydevelopers.trendinggitrepos.ui.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.work.WorkManager
import com.raydevelopers.trendinggitrepos.model.BuiltBy
import com.raydevelopers.trendinggitrepos.model.TrendingRepositoryListObject
import com.raydevelopers.trendinggitrepos.repo.AppRepository
import junit.framework.Assert.assertEquals
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner
import org.powermock.modules.junit4.PowerMockRunnerDelegate

@RunWith(PowerMockRunner::class)
@PowerMockRunnerDelegate(MockitoJUnitRunner::class)
@PrepareForTest(
    Application::class,
    WorkManager::class
)
class TrendingRepoViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val appRepository = Mockito.mock(AppRepository::class.java)
    private val workManager = Mockito.mock(WorkManager::class.java)
    lateinit var trendingViewModel: TrendingRepoViewModel


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.trendingViewModel =
            TrendingRepoViewModel(this.appRepository, this.workManager)
    }

    /*
    Verify workmanager state whrn called
     */
    private fun getFalseWorkManagerLiveData(): LiveData<Boolean> {
        return MutableLiveData<Boolean>().apply { value = false }
    }

    private fun getTrueWorkManagerLiveData(): LiveData<Boolean> {
        return MutableLiveData<Boolean>().apply { value = true }
    }

    @Test
    fun getRepo_success_AppRepositoryLiveData_true() {

        Mockito.`when`(trendingViewModel.getWorkManagerStateLiveData())
            .thenReturn(getTrueWorkManagerLiveData())
        // add observer
        val observer = Mockito.mock(Observer::class.java) as Observer<Boolean>
        trendingViewModel.getWorkManagerStateLiveData().observeForever(observer)

        // starting point
        trendingViewModel.runWorkManagerTask()
        // verify
        assertEquals(true, trendingViewModel.getWorkManagerStateLiveData().value)

    }

    @Test
    fun getRepo_failure_AppRepositoryLiveData_false() {

        Mockito.`when`(trendingViewModel.getWorkManagerStateLiveData())
            .thenReturn(getFalseWorkManagerLiveData())

        // add observer
        val observer = Mockito.mock(Observer::class.java) as Observer<Boolean>
        trendingViewModel.getWorkManagerStateLiveData().observeForever(observer)

        // starting point
        trendingViewModel.runWorkManagerTask()
        //verify
        assertEquals(false, trendingViewModel.getWorkManagerStateLiveData().value)

    }

    private fun getRepoSuccessResult(): LiveData<List<TrendingRepositoryListObject>> {
        var arrayList = ArrayList<TrendingRepositoryListObject>()
        var arrayList2 = ArrayList<BuiltBy>()
        arrayList2.add(BuiltBy("", "", ""))
        arrayList.add(
            TrendingRepositoryListObject(
                1, "rohan",
                "klkl", arrayList2, 2, "", 3, "",
                "", "", 4, ""
            )
        )
        var data = MutableLiveData<List<TrendingRepositoryListObject>>()
        data.value = arrayList
        return data
    }

    private fun getNegativeResult(): LiveData<List<TrendingRepositoryListObject>> {
        return MutableLiveData()
    }

    // for getting sucess result from repo
    @Test
    fun getRepo_Success() {

        Mockito.`when`(trendingViewModel.getTrendingListLiveData())
            .thenReturn(getRepoSuccessResult())
        val observer =
            Mockito.mock(Observer::class.java) as Observer<List<TrendingRepositoryListObject>>
        trendingViewModel.getTrendingListLiveData().observeForever(observer)

        trendingViewModel.runWorkManagerTask()
        assertThat(trendingViewModel.getTrendingListLiveData().value, (not(nullValue())))

    }

    // for getting failure result from repo
    @Test
    fun getRepo_Failure() {
        Mockito.`when`(trendingViewModel.getTrendingListLiveData())
            .thenReturn(getNegativeResult())
        val observer =
            Mockito.mock(Observer::class.java) as Observer<List<TrendingRepositoryListObject>>
        trendingViewModel.getTrendingListLiveData().observeForever(observer)

        trendingViewModel.runWorkManagerTask()
        assertThat(trendingViewModel.getTrendingListLiveData().value, (nullValue()))
    }

}