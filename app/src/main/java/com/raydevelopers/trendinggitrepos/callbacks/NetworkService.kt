package com.raydevelopers.trendinggitrepos.callbacks

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface NetworkService {
    @GET
    fun getRequest(@Url var1: String, @QueryMap var3: Map<String, String>): Call<ResponseBody>
}