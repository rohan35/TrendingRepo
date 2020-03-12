package com.raydevelopers.trendinggitrepos.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raydevelopers.trendinggitrepos.callbacks.NetworkService
import com.raydevelopers.trendinggitrepos.network.NetworkResponse
import com.raydevelopers.trendinggitrepos.network.RetrofitService

object AppRepository {
    fun getCall(
        subUrl: String,
        queryMap: Map<String, String>,
        objectClass: Class<*>
    ): LiveData<Any> {
        var response = MutableLiveData<Any>()
        val call =
            (RetrofitService.createService(NetworkService::class.java) as NetworkService).getRequest(
                subUrl,
                queryMap
            );
        val networkResponse = NetworkResponse(response, objectClass)
        call.enqueue(networkResponse)
        return response
    }
}