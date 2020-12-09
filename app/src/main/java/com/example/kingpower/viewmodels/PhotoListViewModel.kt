package com.example.kingpower.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.kingpower.models.PhotoModel
import com.example.kingpower.service.repositories.PhotosRepository
import com.example.kingpower.utils.ApiRespCallback
import com.example.kingpower.utils.NetworkHelper

class PhotoListViewModel(private val app: Application) : AndroidViewModel(app) {

    private var photosRepository: MutableLiveData<List<PhotoModel>> =
        MutableLiveData<List<PhotoModel>>().apply { value = emptyList() }
    val showApiError = MutableLiveData<String>()
    val showProgressBar = MutableLiveData(true)
    val showNetworkError: MutableLiveData<Boolean> = MutableLiveData()

    fun downloadPhotoList(): MutableLiveData<List<PhotoModel>> {
        if (NetworkHelper.isOnline(app.baseContext)) {
            photosRepository =
                PhotosRepository.getInstance().downloadPhotos(object : ApiRespCallback {
                    override fun onNetworkFailure(th: Throwable) {
                        showApiError.value = th.message
                        showProgressBar.value = false
                    }

                    override fun onNetworkSuccess() {
                        showProgressBar.value = false
                    }
                })
        } else {
            showNetworkError.value = true
        }
        return photosRepository
    }

}