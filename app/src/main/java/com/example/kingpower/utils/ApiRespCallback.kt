package com.example.kingpower.utils

interface ApiRespCallback {

    fun onNetworkSuccess()
    fun onNetworkFailure(th : Throwable)

}