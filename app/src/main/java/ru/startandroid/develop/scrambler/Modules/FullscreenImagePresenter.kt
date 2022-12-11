package ru.startandroid.develop.scrambler.Modules

import android.graphics.Bitmap
import ru.startandroid.develop.scrambler.Modules.General.Router

open class FullscreenImagePresenter <V: MVPView>{

    var fullscreen : Bitmap? = null;
    public fun saveFullscreenImage(bitmap: Bitmap){
        fullscreen = bitmap
    }
    fun loadFullscreenImage(): Bitmap? {
        return Router.fullscreen
    }
}