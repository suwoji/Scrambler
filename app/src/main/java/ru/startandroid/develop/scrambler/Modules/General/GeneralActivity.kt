package ru.startandroid.develop.scrambler.UI

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import me.tatarka.inject.annotations.Inject
import ru.startandroid.develop.scrambler.*
import kotlin.collections.ArrayList

//TODO: rename Presenter
//TODO: to figure out when to init presenter (see android MVP guides)
//TODO: copy MVPPresenter(+ MVPView). Every view conform to protocol MVPView
//TODO: слой view дергает OnAttach OnDetach у presenter
//TODO: словить момент когда создается GeneralActivity
// на этом моменте создать GeneralPresenter и его проставить в GeneralActivity

class GeneralActivity : GeneralMVPView, AppCompatActivity() {
    private var images: ArrayList<Bitmap>? = ArrayList<Bitmap>()
    var adapter: GalleryAdapter? = null

    internal lateinit var presenter: GeneralPresenter<GeneralMVPView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general)
        presenter.onAttach(this)
        supportActionBar!!.hide()

        val intent = Intent(applicationContext, FullImageActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        var list: GridView = findViewById(R.id.GalleryList)
        list.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            applicationContext.startActivity(intent)
        })

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
            presenter.didPickImageFromGallery(applicationContext, data?.data)
//            val image: ImageView = findViewById(R.id.imageView)
//            image.setImageURI(data?.data)
//            image.buildDrawingCache()
        }
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    override fun showProgress() {
        TODO("Not yet implemented")
    }

    override fun hideProgress() {


    }

//    override fun showProgress() {
//        TODO("Not yet implemented")
//    }
//
//    override fun hideProgress() {
//        TODO("Not yet implemented")
//    }

}