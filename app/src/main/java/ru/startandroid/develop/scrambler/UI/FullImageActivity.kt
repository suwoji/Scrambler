package ru.startandroid.develop.scrambler.UI

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import ru.startandroid.develop.scrambler.R

class FullImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        super.onCreate(savedInstanceState)
        val layoutParams = window.attributes
        layoutParams.dimAmount = 0.75f
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        window.attributes = layoutParams
        window.allowEnterTransitionOverlap = true
        setContentView(R.layout.activity_full_image)
    }
}