package ru.startandroid.develop.scrambler.UI

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import ru.startandroid.develop.scrambler.GalleryAdapter
import ru.startandroid.develop.scrambler.R
import java.io.File
import java.io.FileOutputStream


class GeneralActivity : AppCompatActivity() {
    private var images: ArrayList<Bitmap>? =  ArrayList<Bitmap>();
//    private lateinit var list: GridView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_general)
        supportActionBar!!.hide()

    val intent = Intent(applicationContext, FullImageActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

    var list: GridView = findViewById(R.id.GalleryList)
    list.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
        applicationContext.startActivity(intent)
    })

    var passwordInfo: SharedPreferences
        val plus: Button = findViewById(R.id.addFileButton)
        plus.setOnClickListener{
            pickImageFromGallery()

        }
    }
    var n = 0

//    private fun checkPermissionForImage() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if ((checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
//                && (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
//            ) {
//                val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
//                val permissionCoarse = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//
//                requestPermissions(permission, 1001) // GIVE AN INTEGER VALUE FOR PERMISSION_CODE_READ LIKE 1001
//                requestPermissions(permissionCoarse, 1002) // GIVE AN INTEGER VALUE FOR PERMISSION_CODE_WRITE LIKE 1002
//            } else {
//                pickImageFromGallery()
//            }
//        }
//    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1000) // GIVE AN INTEGER VALUE FOR IMAGE_PICK_CODE LIKE 1000
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1000) {
            // I'M GETTING THE URI OF THE IMAGE AS DATA AND SETTING IT TO THE IMAGEVIEW
            n += 1
            val image: ImageView = findViewById(R.id.imageView)
            image.setImageURI(data?.data)
            image.buildDrawingCache()
            val bmap: Bitmap = image.drawingCache
            images?.add(image.drawingCache);
            var list: GridView = findViewById(R.id.GalleryList)
            var adapter = GalleryAdapter (applicationContext, images)
            list.adapter = adapter;
            saveImage(bmap, n.toString() + ".jpg", "Original", 100)
//            val bm = Bitmap.createScaledBitmap(bmap, 1000, 1000, false);
            saveImage(bmap, n.toString() + ".jpg", "Preview", 50)
//            saveImagePreview(bm, n.toString() + ".jpg")

        }
    }

    private fun encrypt(uri: Uri): Uri?{
        Thread.sleep(1000)
        val decryptUri = uri.buildUpon().clearQuery()
        replaceUriParameter(uri,"1","/storage/emulated/0/Android/data/ru.startandroid.develop.scrambler/cache/preview")
        return uri
    }

    private fun decrypt(uri: Uri): Uri?{
        Thread.sleep(1000)
        val decryptUri = uri.buildUpon().clearQuery()
        replaceUriParameter(uri,"1", "/storage/emulated/0/Android/data/ru.startandroid.develop.scrambler/cache/original")
        return uri
    }

    private fun saveImage(image: Bitmap, fileName: String, packageName: String, quality: Int) {
        val imagesFolder = File(applicationContext.externalCacheDir, packageName)
        var uri: Uri? = null

        imagesFolder.mkdirs()
        val file = File(imagesFolder, fileName)
        val stream = FileOutputStream(file)
        image.compress(Bitmap.CompressFormat.JPEG, quality, stream)
        stream.flush()
        stream.close()
//        uri = FileProvider.getUriForFile(applicationContext, "original", file)
//
//        return uri
    }


//    private fun saveImagePreview(image: Bitmap, fileName: String) {
//        val imagesFolder = File(applicationContext.cacheDir, "preview")
//        var uri: Uri? = null
////        try {
//            imagesFolder.mkdirs()
//            val file = File(imagesFolder, fileName)
//            val stream = FileOutputStream(file)
//            image.compress(Bitmap.CompressFormat.PNG, 90, stream)
//            stream.flush()
//            stream.close()
////            uri = FileProvider.getUriForFile(applicationContext, "preview", file)
////        } catch (e: IOException) {
////        }
////        return uri
//    }

    private fun replaceUriParameter(uri: Uri, key: String, newValue: String): Uri? {
        val params = uri.queryParameterNames
        val newUri = uri.buildUpon().clearQuery()
        for (param in params) {
            newUri.appendQueryParameter(
                param,
                if (param == key) newValue else uri.getQueryParameter(param)
            )
        }
        return newUri.build()
    }


}