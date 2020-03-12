package com.raydevelopers.trendinggitrepos.database

import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import java.util.Collections.emptyList
import com.google.gson.Gson
import com.raydevelopers.trendinggitrepos.model.BuiltBy
import java.util.*


class DataTypeConverter {
    private val gson = Gson()
    @TypeConverter
    fun stringToList(data: String?): List<BuiltBy> {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<BuiltBy>>() {

        }.type

        return gson.fromJson<List<BuiltBy>>(data, listType)
    }

    @TypeConverter
    fun listToString(someObjects: List<BuiltBy>): String {
        return gson.toJson(someObjects)
    }
}