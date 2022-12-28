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
    var position: Int? = null
    fun saveFullscreenImage(bitmap: Bitmap, pos: Int){
        fullscreen = bitmap
        position = pos
    }

    fun loadFullscreenImage(): Bitmap? {
        return fullscreen
    }


    fun getPos():Int?{
        return position
    }

    fun setStatus(status: Boolean){
        lockStatus = status
    }

    fun getStatus(): Boolean{
        return lockStatus
    }
    var remove = false
    fun setRemoveTask(status: Boolean){
        remove = status
    }

    fun getRemoveTask(): Boolean{
        return remove
    }
}