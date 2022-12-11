package ru.startandroid.develop.scrambler.UI

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.startandroid.develop.scrambler.Modules.General.Router
import ru.startandroid.develop.scrambler.Modules.General.View.GeneralGridKotlinAdapter
import ru.startandroid.develop.scrambler.R
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.StandardCopyOption


class FullImageActivity : AppCompatActivity() {
    lateinit var image: ImageView
    lateinit var shareButton: Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(ru.startandroid.develop.scrambler.R.anim.fade_in, ru.startandroid.develop.scrambler.R.anim.fade_out)
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        val layoutParams = window.attributes
        layoutParams.dimAmount = 0.75f
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        window.attributes = layoutParams
        window.allowEnterTransitionOverlap = true
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
                showPopupMenu(view)
            }
        })
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

    override fun onStop() {
        super.onStop()
            Router.setStatus(true)
    }

    override fun onResume() {
        super.onResume()
        if (Router.getStatus()){
            val intent2 = Intent(applicationContext, PasswordActivity::class.java)
            intent2.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            applicationContext.startActivity(intent2)
        }
    }
}
