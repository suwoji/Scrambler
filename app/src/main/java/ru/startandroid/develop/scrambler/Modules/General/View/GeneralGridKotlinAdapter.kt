package ru.startandroid.develop.scrambler.Modules.General.View

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ImageView


//import ru.startandroid.develop.scrambler.UI.FullImageActivity

class GeneralGridKotlinAdapter(private val mContext: Context, del: GeneralGridAdapterDelegate, width: Int) : BaseAdapter() {

     private var delegate: GeneralGridAdapterDelegate = del
        var screenWidth = width
    init {
    }

    override fun getCount(): Int {
        return delegate.cellsCount()
    }

    override fun getItem(position: Int): Any {
        return delegate.imageForCell(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // create a new ImageView for each item referenced by the Adapter
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        for( int i = 0; i < mThumbIds.size(); i++){
        val imageView: ImageView
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = ImageView(mContext)
            imageView.layoutParams = AbsListView.LayoutParams(screenWidth/3 - 10, screenWidth/3 - 10)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        } else {
            imageView = convertView as ImageView
        }
//        val intent = Intent(mContext, FullImageActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        imageView.setImageBitmap(delegate.imageForCell(position))

//        imageView.setOnClickListener { v ->
//            when (v.id) {
//                R.id.imageView -> {
//                    mContext.startActivity(intent)
//                }
//            }
//        }

        return imageView
    } // references to our images
}