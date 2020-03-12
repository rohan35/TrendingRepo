package com.raydevelopers.trendinggitrepos.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {

    companion object {
        private var mRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://localhost/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        fun <T> createService(serviceClass: Class<T>): T {
            return this.mRetrofit.create(serviceClass)
        }
    }



}