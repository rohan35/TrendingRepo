package com.raydevelopers.trendinggitrepos.network

import androidx.lifecycle.MutableLiveData
import okhttp3.Headers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkResponse(var responseData: MutableLiveData<Any>, var objectClass: Class<*>):
    Callback<ResponseBody> {
    override fun onFailure(call: Call<ResponseBody>?, t: Throwable) {
        this.responseData.postValue(NetworkResource.error(null as Any?, t, 0, null as Headers))
    }

    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>) {
        var body:String
        if (response?.body() != null && null != (response.body() as ResponseBody).contentType() && null != (response.body() as ResponseBody).contentType().subtype() && (response.body() as ResponseBody).contentType().subtype().equals(
                "json",
                ignoreCase = true
            )
        ) {
            body = (response.body() as ResponseBody).string()
            this.responseData.postValue(
                NetworkResource.success(
                    NetworkUtils.getModelFromJsonString(
                        body,
                        this.objectClass
                    ), response.code(), response.headers()
                )
            )

        }
        else
        {
            body = response?.errorBody().toString()
            this.responseData.postValue(
                NetworkResource.error(
                    NetworkUtils.getModelFromJsonString(
                        body,
                        this.objectClass
                    ), null as Throwable,response.code(), response.headers()
                )
            )
        }
    }
}