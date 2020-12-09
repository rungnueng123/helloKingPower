package com.example.kingpower.service.network

import com.example.kingpower.config.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiService private constructor() {

    companion object {
        private lateinit var mApiServices: Interface
        private var mInstance: ApiService? = null

        fun getInstance(): ApiService {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = ApiService()
                }
            }
            return mInstance!!
        }
    }

    init {
        val okHttpClient = OkHttpClient().newBuilder().connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mApiServices = retrofit.create(Interface::class.java)
    }

    fun getApiService() = mApiServices
}