package ru.startandroid.develop.scrambler.Modules.General

import android.graphics.Bitmap

object Router {
    var mInstance: Router? = null
    var lockStatus = true

    fun shared(): Router? {
        if (Router.mInstance == null) {
            Router.mInstance = Router
        }
        return Router.mInstance
    }
    var fullscreen: Bitmap? = null
    fun saveFullscreenImage(bitmap: Bitmap){
        fullscreen = bitmap
    }

    fun loadFullscreenImage(): Bitmap? {
        return fullscreen
    }

    fun setStatus(status: Boolean){
        lockStatus = status
    }
    fun getStatus(): Boolean{
        return lockStatus
    }
}