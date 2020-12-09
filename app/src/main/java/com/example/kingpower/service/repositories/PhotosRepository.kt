package com.example.kingpower.service.repositories

import androidx.lifecycle.MutableLiveData
import com.example.kingpower.models.PhotoModel
import com.example.kingpower.service.network.ApiService
import com.example.kingpower.utils.ApiRespCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotosRepository {

    private lateinit var apiCallback: ApiRespCallback
    private lateinit var callPhotos: Call<List<PhotoModel>>
    private var photoList: MutableLiveData<List<PhotoModel>> =
        MutableLiveData<List<PhotoModel>>().apply { value = emptyList() }

    companion object {
        private var mInstance: PhotosRepository? = null
        fun getInstance(): PhotosRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = PhotosRepository()
                }
            }
            return mInstance!!
        }
    }

    fun downloadPhotos(callback: ApiRespCallback): MutableLiveData<List<PhotoModel>> {
        apiCallback = callback
        callPhotos = ApiService.getInstance().getApiService().getPhotos()
        callPhotos.enqueue(object : Callback<List<PhotoModel>> {
            override fun onResponse(
                call: Call<List<PhotoModel>>,
                response: Response<List<PhotoModel>>
            ) {
                photoList.value = response.body()
                apiCallback.onNetworkSuccess()
            }

            override fun onFailure(call: Call<List<PhotoModel>>, t: Throwable) {
                photoList.value = emptyList()
                apiCallback.onNetworkFailure(t)
            }

        })
        return photoList
    }

}