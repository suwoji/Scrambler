package ru.startandroid.develop.scrambler.UI

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.startandroid.develop.scrambler.Modules.General.View.GeneralActivity
import ru.startandroid.develop.scrambler.R

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        if (sharedPreference.getString("correctPassword","Null") == "Null"){
            val intent = Intent(applicationContext, CreatePasswordActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            applicationContext.startActivity(intent)
            finish()
        } else{
            val intent = Intent(applicationContext, GeneralActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            applicationContext.startActivity(intent)
            finish()
        }
    }
}