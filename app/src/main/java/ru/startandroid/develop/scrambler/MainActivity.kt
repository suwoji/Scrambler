package ru.startandroid.develop.scrambler

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, PasswordActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, "message")        }
        startActivity(intent);
    }
}