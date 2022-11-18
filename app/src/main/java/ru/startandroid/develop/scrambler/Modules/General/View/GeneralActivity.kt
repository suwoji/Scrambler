package ru.startandroid.develop.scrambler.Modules.General.View

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import ru.startandroid.develop.scrambler.Model.ImageDBService.queryAllImages
import ru.startandroid.develop.scrambler.Modules.General.Presenter.GeneralPresenter
import ru.startandroid.develop.scrambler.Modules.General.Presenter.GeneralPresenterInterface
import ru.startandroid.develop.scrambler.Modules.MVPView
import ru.startandroid.develop.scrambler.R
import ru.startandroid.develop.scrambler.UI.FullImageActivity

class GeneralActivity : MVPView, GeneralGridAdapterDelegate, AppCompatActivity() {
    var adapter: GeneralGridKotlinAdapter? = null
    lateinit var list: GridView
    var presenter: GeneralPresenterInterface<GeneralActivity> = GeneralPresenter<GeneralActivity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general)
        presenter.onAttach(this)
        supportActionBar!!.hide()

        val intent = Intent(applicationContext, FullImageActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        list = findViewById(R.id.GalleryList)
        list.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            applicationContext.startActivity(intent)
        })
        adapter = GeneralGridKotlinAdapter(applicationContext, this)
        val plus: Button = findViewById(R.id.addFileButton)
        plus.setOnClickListener {
            pickImageFromGallery()
            list.adapter = adapter
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1000)
    }

    //TODO: presenter.didPickImageFromGallery
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1000) {
            Thread.sleep(2000)
//            val image = MediaStore.Images.Media.getBitmap(applicationContext.getContentResolver(), data?.data)
//            val image: ImageView = findViewById(R.id.imageView)
//            image.setImageURI(data?.data)
//            image.buildDrawingCache()
            val bmap: Bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(applicationContext.contentResolver, data?.data!!))
            presenter.didPickImageFromGallery(applicationContext, bmap)
//            val image: ImageView = findViewById(R.id.imageView)
//            image.setImageURI(data?.data)
//            image.buildDrawingCache()
        }
    }
    override fun onResume() {
        super.onResume()
        adapter =
            GeneralGridKotlinAdapter(
                applicationContext,
                this
            )

        list.adapter = adapter
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

    override fun imageForCell(index: Int): Bitmap {
        return presenter.previewImages(applicationContext, index)
    }

}
