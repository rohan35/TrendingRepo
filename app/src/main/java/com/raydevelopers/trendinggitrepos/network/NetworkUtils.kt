package com.raydevelopers.trendinggitrepos.network

import com.google.gson.GsonBuilder

class NetworkUtils {
    companion object
    {
        fun <Any> getModelFromJsonString(response: String, modelClass: Class<Any>): Any? {
            return try {
                val gson = GsonBuilder().create()
                gson.fromJson(response, modelClass)
            } catch (var3: Exception) {
                var3.printStackTrace()
                null
            }

        }
    }
}