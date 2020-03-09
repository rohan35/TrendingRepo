package com.raydevelopers.trendinggitrepos.network

import okhttp3.Headers

class NetworkResource <Any>(var statusCode:Int?, var status:NetworkResource.Status?,var data:Any?, error:Throwable?){
    companion object
    {
        fun <Any> success(data: Any, statusCode: Int, headers: Headers): NetworkResource<Any> {
            return NetworkResource(
                statusCode,
                NetworkResource.Status.SUCCESS,
                data,
                null as Throwable?
            )
        }

        fun <Any> error(data: Any, error: Throwable, statusCode: Int, headers: Headers): NetworkResource<Any> {
            return NetworkResource(statusCode, NetworkResource.Status.ERROR, data, error)
        }
    }
    enum class Status private constructor() {
        SUCCESS,
        ERROR
    }
}