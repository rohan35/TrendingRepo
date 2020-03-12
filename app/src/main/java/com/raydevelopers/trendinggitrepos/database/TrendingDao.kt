package com.raydevelopers.trendinggitrepos.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raydevelopers.trendinggitrepos.model.TrendingRepositoryListObject

@Dao
interface TrendingDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(trendingRepositoryListObject: List<TrendingRepositoryListObject>)

    @Query("SELECT * FROM TrendingRepositoryListObject")
    fun getTrendingRepoList(): LiveData<List<TrendingRepositoryListObject>>
}