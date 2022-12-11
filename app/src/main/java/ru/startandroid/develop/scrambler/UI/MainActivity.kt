package ru.startandroid.develop.scrambler.UI

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import androidx.appcompat.app.AppCompatActivity
import ru.startandroid.develop.scrambler.Modules.General.View.GeneralActivity
import ru.startandroid.develop.scrambler.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, GeneralActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, "message")        }
        startActivity(intent);
    }
}