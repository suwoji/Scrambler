package ru.startandroid.develop.scrambler.UI

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.startandroid.develop.scrambler.R

class PasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)
        val passwordEditText: EditText = findViewById(R.id.newPasswordPlainText);
        Toast.makeText(this, passwordEditText.toString(), Toast.LENGTH_SHORT);

        val one: Button = findViewById(R.id.button1);
        one.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "1");
//    TODO   password checking
        }

        val two: Button = findViewById(R.id.button2);
        two.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "2");
        }

        val three: Button = findViewById(R.id.button3);
        three.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "3");
        }

        val four: Button = findViewById(R.id.button4);
        four.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "4");
        }

        val five: Button = findViewById(R.id.button5);
        five.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "5");
        }

        val six: Button = findViewById(R.id.button6);
        six.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "6");
        }

        val seven: Button = findViewById(R.id.button7);
        seven.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "7");
        }

        val eight: Button = findViewById(R.id.button8);
        eight.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "8");
        }

        val nine: Button = findViewById(R.id.button9);
        nine.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "9");
        }

        val zero: Button = findViewById(R.id.button0);
        zero.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "0");
        }
    }
}