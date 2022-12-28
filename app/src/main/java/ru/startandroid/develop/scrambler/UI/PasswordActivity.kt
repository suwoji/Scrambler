package ru.startandroid.develop.scrambler.UI

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.startandroid.develop.scrambler.Modules.General.Router
import ru.startandroid.develop.scrambler.R













class PasswordActivity : AppCompatActivity() {
    var correctPassword : String = "qwer"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)
        val passwordEditText: EditText = findViewById(R.id.PasswordPlainText);
        passwordEditText.setClickable(false);
        Toast.makeText(this, passwordEditText.toString(), Toast.LENGTH_SHORT);

        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()

        correctPassword = sharedPreference.getString("correctPassword", "NullPassword").toString()

        val delete: Button = findViewById(R.id.passwordDeleteButton);
        delete.setOnClickListener{
            val text: String = passwordEditText.getText().toString();
            passwordEditText.setText(text.substring(0, text.length - 1));
            if (passwordEditText.text.isEmpty()){delete.visibility= View.INVISIBLE}
        }

        val one: Button = findViewById(R.id.button1);
        one.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "1");
            if(passwordEditText.text.isNotEmpty()){delete.visibility= View.VISIBLE}
            checkPassword(passwordEditText.text.toString())
        }

        val two: Button = findViewById(R.id.button2);
        two.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "2");
            if(passwordEditText.text.isNotEmpty()){delete.visibility= View.VISIBLE}
            checkPassword(passwordEditText.text.toString())
        }

        val three: Button = findViewById(R.id.button3);
        three.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "3");
            if(passwordEditText.text.isNotEmpty()){delete.visibility= View.VISIBLE}
            checkPassword(passwordEditText.text.toString())
        }

        val four: Button = findViewById(R.id.button4);
        four.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "4");
            if(passwordEditText.text.isNotEmpty()){delete.visibility= View.VISIBLE}
            checkPassword(passwordEditText.text.toString())
        }

        val five: Button = findViewById(R.id.button5);
        five.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "5");
            if(passwordEditText.text.isNotEmpty()){delete.visibility= View.VISIBLE}
            checkPassword(passwordEditText.text.toString())
        }

        val six: Button = findViewById(R.id.button6);
        six.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "6");
            if(passwordEditText.text.isNotEmpty()){delete.visibility= View.VISIBLE}
            checkPassword(passwordEditText.text.toString())
        }

        val seven: Button = findViewById(R.id.button7);
        seven.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "7");
            if(passwordEditText.text.isNotEmpty()){delete.visibility= View.VISIBLE}
            checkPassword(passwordEditText.text.toString())
        }

        val eight: Button = findViewById(R.id.button8);
        eight.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "8");
            if(passwordEditText.text.isNotEmpty()){delete.visibility= View.VISIBLE}
            checkPassword(passwordEditText.text.toString())
        }

        val nine: Button = findViewById(R.id.button9);
        nine.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "9");
            if(passwordEditText.text.isNotEmpty()){delete.visibility= View.VISIBLE}
            checkPassword(passwordEditText.text.toString())
        }

        val zero: Button = findViewById(R.id.button0);
        zero.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "0");
            if(passwordEditText.text.isNotEmpty()){delete.visibility= View.VISIBLE}
            checkPassword(passwordEditText.text.toString())
            checkPassword(passwordEditText.text.toString())
        }
    }
    fun checkPassword(password: String){
        if (password.length == 4){
            if (password == correctPassword){
                Router.setStatus(false)
                finish()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAndRemoveTask()
    }
}