package ru.startandroid.develop.scrambler.UI

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import ru.startandroid.develop.scrambler.Model.ImageDBService
import ru.startandroid.develop.scrambler.Modules.General.Router
import ru.startandroid.develop.scrambler.Modules.General.View.GeneralActivity
import ru.startandroid.develop.scrambler.R
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess


class FullImageActivity : AppCompatActivity() {
    lateinit var image: ImageView
    lateinit var shareButton: Button
    var temp2 = 0
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
//        overridePendingTransition(ru.startandroid.develop.scrambler.R.anim.fade_in, ru.startandroid.develop.scrambler.R.anim.fade_out)
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        val layoutParams = window.attributes
//        layoutParams.dimAmount = 0.75f
//        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
//        window.attributes = layoutParams
//        window.allowEnterTransitionOverlap = true
        setContentView(ru.startandroid.develop.scrambler.R.layout.activity_full_image)
        image = findViewById(R.id.fullScreenImage)
        image.setImageBitmap(Router.loadFullscreenImage())
        shareButton = findViewById(R.id.fullscreenShareButton)

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "тут что-то с картинкой надо")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        shareButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                temp2=+1
                Router.setStatus(false)
                moveImageToGallery(Router.loadFullscreenImage()!!)
                finish()
//                showPopupMenu(view)
            }
        })
    }

    fun deleteEncryptedImages (info: ru.startandroid.develop.scrambler.Model.ImageInfo){
        var prevDelete = File(info.previewUri)
        prevDelete.delete()
        var origDelete = File(info.originalUri)
        origDelete.delete()
        ImageDBService.deleteImage(Router.getPos()!!)
    }

    private fun moveImageToGallery(drawable: Bitmap) {
        val file = getDisc()

        if (!file.exists() && !file.mkdirs()) {
            file.mkdir()
        }

        val simpleDateFormat = SimpleDateFormat("yyyymmsshhmmss")
        val date = simpleDateFormat.format(Date())
        val name = "IMG" + date + ".jpg"
        val fileName = file.absolutePath + "/" + name
        val newFile = File(fileName)

        try {
//            val draw = drawable as BitmapDrawable
            val bitmap = drawable
            val fileOutPutStream = FileOutputStream(newFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutPutStream)
            Toast.makeText(applicationContext, "File saved succesfully", Toast.LENGTH_SHORT)
                .show()
//             = newFile
            fileOutPutStream.flush()
            fileOutPutStream.close()

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        deleteEncryptedImages(ImageDBService.queryImage(Router.getPos()!!))
    }

    private fun getDisc(): File {
        val file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        return File(file, "Camera")
    }

    fun showPopupMenu(v: View?) {

        val from = File("src.txt")
        val to = File("dest.txt")
        val popupMenu = PopupMenu(this, v)
        popupMenu.inflate(R.menu.popup)
        popupMenu
            .setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    return when (item.getItemId()) {
                        R.id.menu1 -> {
                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, "тут что-то с картинкой надо")
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, null)
                            applicationContext.startActivity(shareIntent)
                            false
                        }
                        R.id.menu2 -> {
                            try {
                                moveFile(from, to)
                                println("File moved successfully.")
                            } catch (ex: IOException) {
                                ex.printStackTrace()
                            }
                            true
                        }
                        else -> false
                    }
                }
            })
    }
    fun moveFile(src: File, dest: File) {
        Files.move(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING)
    }
    var temp = 0
    override fun onBackPressed() {
        Router.setStatus(false)
        temp+=1
       finish()
    }

    override fun onStop() {
        super.onStop()
        if(temp2==0) {
            Router.setRemoveTask(true)
//        System.exit(0)
//        finishAndRemoveTask()
//        finish()
            finishAffinity()
            this.finishAffinity()
//        exitProcess(0)
//        if (temp==0)
//            Router.setStatus(true)
        }
    }

    override fun onResume() {
        super.onResume()
//        Router.setStatus(true)
//        if (Router.getStatus()){
//            val intent2 = Intent(applicationContext, PasswordActivity::class.java)
//            intent2.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            applicationContext.startActivity(intent2)
//        }
    }
}
