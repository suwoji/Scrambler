package ru.startandroid.develop.scrambler.Modules.General.Presenter

//import ru.startandroid.develop.scrambler.UI.FullImageActivity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.widget.Toast
import ru.startandroid.develop.scrambler.Model.ImageDBService
import ru.startandroid.develop.scrambler.Modules.General.Router
import ru.startandroid.develop.scrambler.Modules.MVPView
import ru.startandroid.develop.scrambler.Scrambler
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

    override fun didPickImageFromGallery(context: Context, image: Bitmap, data: Uri){
//            images?.add(image)
        var w = image.width
        var h = image.height
        var ans = 1
        if (w > h) {
            ans = w / 256
            h /= ans
            w = 256
        }else {
            ans = h / 256
            w /= ans
            h = 256
        }
        var uniqueID = UUID.randomUUID().toString()
            var orig : Uri = saveImage(context, image, uniqueID + ".jpg", "Original", 100)
            var prev : Uri = saveImage(context, Bitmap.createScaledBitmap(image, w,h, false), uniqueID + ".jpg", "Preview", 100)
            if (scrambler.encrypt(context, orig.toString()) && scrambler.encrypt(context, prev.toString())) {
                var newPrev : Uri = Uri.parse(prev.path + ".cyp")
                var newOrig : Uri = Uri.parse(orig.path + ".cyp")
                loadImageInfoToDataBase(uniqueID, newPrev, newOrig)


                    val fdelete: File = File(getImageFilePath(context, data))
//                val fdelete: File = File(data.path)
//                fileDelete(Uri.parse(fdelete.path), context)
//                context.contentResolver.delete(Uri.fromFile(fdelete), null,null)
                fdelete.delete()
                context.sendBroadcast(
                    Intent(
                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(
                            fdelete
                        )
                    )
                )

                if (fdelete.delete()) {
                        if (fdelete.delete()) {
                            val deleted2: Boolean = fdelete.getCanonicalFile().delete()
                            if (!deleted2) {
                                context.deleteFile(fdelete.getName())
                            }

                            System.out.println("file Deleted :" + data!!.getPath())
                        } else {
                            System.out.println("file not Deleted :" + data!!.getPath())
                        }
                    }
            } else {
                //Encrypt failed
            }
    }

    @SuppressLint("Range")
    open fun getImageFilePath(context: Context, uri: Uri?): String? {
        var path: String? = null /*from  w ww  . j av  a2s  .c o m*/
        var cursor: Cursor? = null
        try {
            cursor = context.contentResolver.query(
                uri!!,
                arrayOf(MediaStore.Images.Media.DATA),
                null,
                null,
                null
            )
            cursor?.moveToFirst()
            path = cursor?.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
        } finally {
            cursor?.close()
        }
        return path
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
    override fun saveFullscreenImage(bitmap: Bitmap, pos: Int){
        Router.shared()?.saveFullscreenImage(bitmap, pos)
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

    override fun originalImage(context: Context, index: Int): Bitmap {
        val uri = imageDBService.queryAllImages()[index].originalUri
        val result = scrambler.decrypt(context, uri.toString())
        return result
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