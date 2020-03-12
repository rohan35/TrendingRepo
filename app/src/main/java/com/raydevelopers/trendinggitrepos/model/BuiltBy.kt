package com.raydevelopers.trendinggitrepos.model

import androidx.room.ColumnInfo

data class BuiltBy(
    val avatar: String,
    val href: String,
    val username: String
)