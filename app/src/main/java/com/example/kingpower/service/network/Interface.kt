package com.example.kingpower.service.network

import com.example.kingpower.models.PhotoModel
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET

interface Interface {

    @GET("photos")
    fun getPhotos(): Call<List<PhotoModel>>

}