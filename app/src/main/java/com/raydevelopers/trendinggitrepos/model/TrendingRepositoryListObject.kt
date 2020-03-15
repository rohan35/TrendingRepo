package com.raydevelopers.trendinggitrepos.model

import androidx.room.*
import com.raydevelopers.trendinggitrepos.database.DataTypeConverter

@Entity
data class TrendingRepositoryListObject(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val author: String,
    val avatar: String,
    @TypeConverters(DataTypeConverter::class)
    val builtBy: List<BuiltBy>,
    val currentPeriodStars: Int,
    val description: String,
    val forks: Int,
    val language: String,
    val languageColor: String,
    val name: String,
    val stars: Int,
    val url: String
)
{
    fun getDescriptionAndUrl():String
    {
        return "$description($url)"
    }
}