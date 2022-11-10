package ru.startandroid.develop.scrambler.UI

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import ru.startandroid.develop.scrambler.R

class CreatePasswordActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_password)
        val passwordEditText: EditText = findViewById(R.id.newPasswordPlainText)
        Toast.makeText(this, passwordEditText.toString(), Toast.LENGTH_SHORT)
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()

        val empty: Button = findViewById(R.id.emptyButton);
        empty.setOnClickListener{
            if(passwordEditText.text.length == 4) {
                editor.putString("username", passwordEditText.getText().toString())
                editor.commit()
            } else {
                Toast.makeText(applicationContext, "short password", LENGTH_SHORT).show()
            }
        }

        val delete: Button = findViewById(R.id.deleteButton);
        delete.setOnClickListener{
            val text: String = passwordEditText.getText().toString();
            passwordEditText.setText(text.substring(0, text.length - 1));
            if (passwordEditText.text.length == 0){delete.visibility= View.INVISIBLE}
        }

        val one: Button = findViewById(R.id.button1);
        one.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "1");
            if(passwordEditText.text.length < 4){empty.visibility= View.VISIBLE}
            if(passwordEditText.text.length > 0){delete.visibility= View.VISIBLE}
        }

        val two: Button = findViewById(R.id.button2);
        two.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "2");
            if(passwordEditText.text.length == 4){empty.visibility= View.VISIBLE}
            if(passwordEditText.text.length > 0){delete.visibility= View.VISIBLE}
        }

        val three: Button = findViewById(R.id.button3);
        three.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "3");
            if(passwordEditText.text.length == 4){empty.visibility= View.VISIBLE}
            if(passwordEditText.text.length > 0){delete.visibility= View.VISIBLE}
        }

        val four: Button = findViewById(R.id.button4);
        four.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "4");
            if(passwordEditText.text.length == 4){empty.visibility= View.VISIBLE}
            if(passwordEditText.text.length > 0){delete.visibility= View.VISIBLE}
        }

        val five: Button = findViewById(R.id.button5);
        five.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "5");
            if(passwordEditText.text.length == 4){empty.visibility= View.VISIBLE}
            if(passwordEditText.text.length > 0){delete.visibility= View.VISIBLE}
        }

        val six: Button = findViewById(R.id.button6);
        six.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "6");
            if(passwordEditText.text.length == 4){empty.visibility= View.VISIBLE}
            if(passwordEditText.text.length > 0){delete.visibility= View.VISIBLE}
        }

        val seven: Button = findViewById(R.id.button7);
        seven.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "7");
            if(passwordEditText.text.length == 4){empty.visibility= View.VISIBLE}
            if(passwordEditText.text.length > 0){delete.visibility= View.VISIBLE}
        }

        val eight: Button = findViewById(R.id.button8);
        eight.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "8");
            if(passwordEditText.text.length == 4){empty.visibility= View.VISIBLE}
            if(passwordEditText.text.length > 0){delete.visibility= View.VISIBLE}
        }

        val nine: Button = findViewById(R.id.button9);
        nine.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "9");
            if(passwordEditText.text.length == 4){empty.visibility= View.VISIBLE}
            if(passwordEditText.text.length > 0){delete.visibility= View.VISIBLE}

        }

        val zero: Button = findViewById(R.id.button0);
        zero.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "0");
            if(passwordEditText.text.length == 4){empty.visibility= View.VISIBLE}
            if(passwordEditText.text.length > 0){delete.visibility= View.VISIBLE}
        }
    }
}