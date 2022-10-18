package ru.startandroid.develop.scrambler

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import androidx.core.content.FileProvider
import me.tatarka.inject.annotations.Inject
import ru.startandroid.develop.scrambler.UI.GeneralActivity
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.collections.ArrayList

class GeneralPresenter <V: MVPView>{
    private var images: ArrayList<Bitmap>? = ArrayList<Bitmap>()

    fun loadImageInfoToDataBase(name: String, previewUri: Uri, originalUri: Uri) {
        ImageDBService.writeImage(name, previewUri, originalUri);
    }

    fun saveImage(context: Context, image: Bitmap, fileName: String, packageName: String, quality: Int): Uri {
        val imagesFolder = File(context.externalCacheDir, packageName)
        var uri: Uri? = null
        imagesFolder.mkdirs()
        val file = File(imagesFolder, fileName)
        val stream = FileOutputStream(file)
        image.compress(Bitmap.CompressFormat.JPEG, quality, stream)
        stream.flush()
        stream.close()
        uri = FileProvider.getUriForFile(context, "original", file)
        return uri
    }

    fun didPickImageFromGallery(context: Context, uri: Uri?){
            val image = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri)
            images?.add(image);
            var uniqueID = UUID.randomUUID().toString()
            var orig : Uri = saveImage(context, image, uniqueID + ".jpg", "Original", 100)
            var prev : Uri = saveImage(context, image, uniqueID + ".jpg", "Preview", 50)
            loadImageInfoToDataBase(uniqueID, prev, orig)
    }

    private var view: V? = null
    private val isViewAttached: Boolean get() = view != null

    fun onAttach(view: V) {
        this.view = view
    }

    fun onDetach() {
        view = null
    }

//    private fun replaceUriParameter(uri: Uri, key: String, newValue: String): Uri? {
//        val params = uri.queryParameterNames
//        val newUri = uri.buildUpon().clearQuery()
//        for (param in params) {
//            newUri.appendQueryParameter(
//                param,
//                if (param == key) newValue else uri.getQueryParameter(param)
//            )
//        }
//        return newUri.build()
//    }
//
//    private fun encrypt(uri: Uri): Uri? {
//        Thread.sleep(1000)
//        val decryptUri = uri.buildUpon().clearQuery()
//        replaceUriParameter(
//            uri,
//            "1",
//            "/storage/emulated/0/Android/data/ru.startandroid.develop.scrambler/cache/preview"
//        )
//        return uri
//    }
//
//
//    private fun decrypt(uri: Uri): Uri? {
//        Thread.sleep(1000)
//        val decryptUri = uri.buildUpon().clearQuery()
//        replaceUriParameter(
//            uri,
//            "1",
//            "/storage/emulated/0/Android/data/ru.startandroid.develop.scrambler/cache/original"
//        )
//        return uri
//    }
}