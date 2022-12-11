package ru.startandroid.develop.scrambler.Modules.General.Presenter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.ContentProviderCompat.requireContext
import ru.startandroid.develop.scrambler.Model.ImageDBService
import ru.startandroid.develop.scrambler.Modules.General.Router
import ru.startandroid.develop.scrambler.Modules.MVPView
import ru.startandroid.develop.scrambler.Scrambler
//import ru.startandroid.develop.scrambler.UI.FullImageActivity
import java.io.File
import java.io.FileOutputStream
import java.util.*

open class GeneralPresenter <V: MVPView>: GeneralPresenterInterface<V>{
    private val imageDBService = ImageDBService
    private val scrambler = Scrambler()
//    private var images: ArrayList<Bitmap>? = ArrayList<Bitmap>()

    fun loadImageInfoToDataBase(
        name: String, previewUri: Uri, originalUri: Uri){
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
        uri =   Uri.parse(file.toString())
//            FileProvider.getUriForFile(context, "Original", file)
        return uri
    }
    fun openFullscreenImage(context: Context){
//        val intent = Intent(context, FullImageActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        context.startActivity(intent)
    }

    override fun didPickImageFromGallery(context: Context, image: Bitmap){
//            images?.add(image)
            var uniqueID = UUID.randomUUID().toString()
            var orig : Uri = saveImage(context, image, uniqueID + ".jpg", "Original", 80)
            var prev : Uri = saveImage(context, image, uniqueID + ".jpg", "Preview", 50)

            if (scrambler.encrypt(context, orig.toString()) && scrambler.encrypt(context, prev.toString())) {
                var newPrev : Uri = Uri.parse(prev.path + ".cyp")
                var newOrig : Uri = Uri.parse(orig.path + ".cyp")
                loadImageInfoToDataBase(uniqueID, newPrev, newOrig)
            } else {
                //Encrypt failed
            }
    }

    private var view: V? = null
    private val isViewAttached: Boolean get() = view != null

//    fun onAttach(view: V) {
//
//    }
//
//    fun onDetach() {
//        view = null
//    }

    override fun imageCount(): Int {
        return imageDBService.queryAllImagesCount()
    }
//    var fullscreen : Bitmap? = null;
    override fun saveFullscreenImage(bitmap: Bitmap){
        Router.shared()?.saveFullscreenImage(bitmap)
    }

    override fun fullSizeImage(context: Context, index: Int): Bitmap {
        val uri = imageDBService.queryAllImages()[index].originalUri
        val result = scrambler.decrypt(context, uri.toString())
//        if (result.second) {
//            val decImageUri = Uri.parse(result)
//            val decImage = ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, decImageUri))
            return result
//        }
//        return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    }

    override fun previewImages(context: Context, index: Int): Bitmap {
        val uri = imageDBService.queryAllImages()[index].previewUri
        val result = scrambler.decrypt(context, uri.toString())
//        if (result.second) {
//            val decImageUri = Uri.parse(result)
//            val decImage = ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, decImageUri))
            return result
//        }
//        return Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    }

    override fun onAttach(view: V?) {
    }

    override fun getView(): V? {
        TODO("Not yet implemented")
    }

    override fun onDetach() {
        view = null
    }
}