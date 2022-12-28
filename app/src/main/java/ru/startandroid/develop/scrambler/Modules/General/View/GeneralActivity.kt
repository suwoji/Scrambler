package ru.startandroid.develop.scrambler.Modules.General.View

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.DisplayMetrics
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import ru.startandroid.develop.scrambler.Modules.General.Presenter.GeneralPresenter
import ru.startandroid.develop.scrambler.Modules.General.Presenter.GeneralPresenterInterface
import ru.startandroid.develop.scrambler.Modules.General.Router
import ru.startandroid.develop.scrambler.Modules.MVPView
import ru.startandroid.develop.scrambler.R
import ru.startandroid.develop.scrambler.UI.FullImageActivity
import ru.startandroid.develop.scrambler.UI.MainActivity
import ru.startandroid.develop.scrambler.UI.PasswordActivity


class GeneralActivity : MVPView, GeneralGridAdapterDelegate, AppCompatActivity() {
    var adapter: GeneralGridKotlinAdapter? = null
    lateinit var list: GridView
    var lock = true
    var width: Int = 0
    var temp: Uri? = null

    var presenter: GeneralPresenterInterface<GeneralActivity> = GeneralPresenter<GeneralActivity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general)
        presenter.onAttach(this)
//        supportActionBar!!.hide()
        supportActionBar!!.title = "Gallery"


        val colorDrawable = ColorDrawable(Color.parseColor("#4161FF"))
        supportActionBar!!.setBackgroundDrawable(colorDrawable)

        val intent = Intent(applicationContext, FullImageActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        if(!checkPermission()){
            requestPermission();
        }

        list = findViewById(R.id.GalleryList)
        list.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            presenter.saveFullscreenImage(originalImageForCell(position), position)
            lock = false
            applicationContext.startActivity(intent)
        })

        val displayMetrics = DisplayMetrics()
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
        width = displayMetrics.widthPixels

        adapter = GeneralGridKotlinAdapter(applicationContext, this, width)
        list.adapter
        val plus: Button = findViewById(R.id.addFileButton)
        plus.setOnClickListener {
            pickImageFromGallery()
            list.adapter = adapter
        }

//        list.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
//            val intent = Intent(applicationContext, FullImageActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            applicationContext.startActivity(intent)
//        })
//        list.setOnItemClickListener { adapter, view, i, l ->        }
    }

    private fun pickImageFromGallery() {
        lock = false
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1000)
    }


    //TODO: presenter.didPickImageFromGallery
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1000) {
            Thread.sleep(2000)
            val bmap: Bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(applicationContext.contentResolver, data?.data!!))
            presenter.didPickImageFromGallery(applicationContext, bmap, data?.data!!)
            temp = data.data
            Router.setStatus(false)
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data =
                    Uri.parse(String.format("package:%s", applicationContext.packageName))
                startActivityForResult(intent, 2296)
            } catch (e: Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                startActivityForResult(intent, 2296)
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(
                this,
                arrayOf(WRITE_EXTERNAL_STORAGE),
                10
            )
        }
    }

    override fun onBackPressed() {
        finishAndRemoveTask()
    }

    private fun checkPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager()
        } else {
            var result = 0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                result = applicationContext.checkSelfPermission(READ_EXTERNAL_STORAGE)
                val result1: Int = applicationContext.checkSelfPermission(WRITE_EXTERNAL_STORAGE)
                return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
            }
        }
        return true
    }
    override fun onResume() {
        super.onResume()
        if (Router.getRemoveTask())
            finishAndRemoveTask()
        else {
            lock = true
            if (Router.getStatus()) {
                lock = false
                val intent2 = Intent(applicationContext, PasswordActivity::class.java)
                intent2.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                applicationContext.startActivity(intent2)
            }
            adapter =
                GeneralGridKotlinAdapter(
                    applicationContext,
                    this,
                    width
                )
            list.adapter = adapter
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


    override fun onStop() {
        super.onStop()

//        finishAndRemoveTask()
        if (lock)
            finishAndRemoveTask()
        Router.setStatus(true)
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    // GeneralGridAdapterDelegate

    override fun cellsCount(): Int {
        val asd = presenter.imageCount()
        println(asd)
        return presenter.imageCount()
    }

    override fun previewImageForCell(index: Int): Bitmap {
        return presenter.previewImages(applicationContext, index)
    }

    fun originalImageForCell(index: Int): Bitmap {
        return presenter.originalImage(applicationContext, index)
    }
}
