package ru.startandroid.develop.scrambler.Modules.General.Presenter

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import ru.startandroid.develop.scrambler.Modules.MVPPresenter
import ru.startandroid.develop.scrambler.Modules.MVPView

interface GeneralPresenterInterface<V: MVPView>: MVPPresenter<V> {
    fun imageCount(): Int
    fun fullSizeImage(context: Context, index: Int): Bitmap
    fun previewImages(context: Context, index: Int): Bitmap
    fun didPickImageFromGallery(context: Context, image: Bitmap)
    fun saveFullscreenImage(bitmap: Bitmap)
}