package com.example.kingpower.common

import com.example.kingpower.models.PhotoModel

 object Singleton {

    private var instance: Singleton? = null
    private var photoModel: PhotoModel? = null

    @Synchronized
    private fun createInstance() {
        if (instance == null) {
            instance = Singleton
        }
    }

    fun getInstance(): Singleton? {
        if (instance == null) createInstance()
        return instance
    }

    fun setPhotoModel(model:PhotoModel) {
        photoModel = model
    }

    fun getPhotoModel(): PhotoModel? {
        return photoModel
    }

    fun clearPhotoModel(){
        photoModel = null
    }
}