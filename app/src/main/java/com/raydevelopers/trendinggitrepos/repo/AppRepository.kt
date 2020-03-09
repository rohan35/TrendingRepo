package com.raydevelopers.trendinggitrepos.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.raydevelopers.trendinggitrepos.callbacks.NetworkService
import com.raydevelopers.trendinggitrepos.network.NetworkResponse
import retrofit2.Retrofit

object AppRepository {
    fun getCall(subUrl:String, queryMap:Map<String, String>,objectClass:Class<*>): LiveData<Any>
    {
        var response = MutableLiveData<Any>()
        val call = Retrofit.Builder().build().create(NetworkService::class.java).getRequest(subUrl,queryMap)
        val networkResponse = NetworkResponse(response,objectClass)
        call.enqueue(networkResponse)
        return response
    }
}