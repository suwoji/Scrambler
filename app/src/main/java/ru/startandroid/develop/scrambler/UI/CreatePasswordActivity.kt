package ru.startandroid.develop.scrambler.UI

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import ru.startandroid.develop.scrambler.Modules.General.View.GeneralActivity
import ru.startandroid.develop.scrambler.R


//TODO исправить макет экрана
// центрировать кнопки
// убрать появление клавиатуры
// изменить дизайн и иконки
// показывать кнопку next в случае подходящего пароля
// кнопка back


class CreatePasswordActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_password)
        val passwordEditText: EditText = findViewById(R.id.newPasswordPlainText)
        Toast.makeText(this, passwordEditText.toString(), Toast.LENGTH_SHORT)
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()

        val empty: Button = findViewById(R.id.emptyButton)
        empty.setOnClickListener{
            if(passwordEditText.text.length == 4) {
                editor.putString("correctPassword", passwordEditText.getText().toString())
                editor.commit()

                val intent = Intent(applicationContext, GeneralActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                applicationContext.startActivity(intent)
                finish()
            } else {
            }
        }

        val delete: Button = findViewById(R.id.deleteButton);
        delete.setOnClickListener{
            val text: String = passwordEditText.getText().toString();
            passwordEditText.setText(text.substring(0, text.length - 1));
            if(passwordEditText.text.length == 0){empty.visibility= View.INVISIBLE}
            if (passwordEditText.text.length == 0){delete.visibility= View.INVISIBLE}
        }

        val one: Button = findViewById(R.id.button1n);
        one.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "1");
            if(passwordEditText.text.length == 4){empty.visibility= View.VISIBLE}
            if(passwordEditText.text.length > 0){delete.visibility= View.VISIBLE}
        }

        val two: Button = findViewById(R.id.button2n);
        two.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "2");
            if(passwordEditText.text.length == 4){empty.visibility= View.VISIBLE}
            if(passwordEditText.text.length > 0){delete.visibility= View.VISIBLE}
        }

        val three: Button = findViewById(R.id.button3n);
        three.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "3");
            if(passwordEditText.text.length == 4){empty.visibility= View.VISIBLE}
            if(passwordEditText.text.length > 0){delete.visibility= View.VISIBLE}
        }

        val four: Button = findViewById(R.id.button4n);
        four.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "4");
            if(passwordEditText.text.length == 4){empty.visibility= View.VISIBLE}
            if(passwordEditText.text.length > 0){delete.visibility= View.VISIBLE}
        }

        val five: Button = findViewById(R.id.button5n);
        five.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "5");
            if(passwordEditText.text.length == 4){empty.visibility= View.VISIBLE}
            if(passwordEditText.text.length > 0){delete.visibility= View.VISIBLE}
        }

        val six: Button = findViewById(R.id.button6n);
        six.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "6");
            if(passwordEditText.text.length == 4){empty.visibility= View.VISIBLE}
            if(passwordEditText.text.length > 0){delete.visibility= View.VISIBLE}
        }

        val seven: Button = findViewById(R.id.button7n);
        seven.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "7");
            if(passwordEditText.text.length == 4){empty.visibility= View.VISIBLE}
            if(passwordEditText.text.length > 0){delete.visibility= View.VISIBLE}
        }

        val eight: Button = findViewById(R.id.button8n);
        eight.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "8");
            if(passwordEditText.text.length == 4){empty.visibility= View.VISIBLE}
            if(passwordEditText.text.length > 0){delete.visibility= View.VISIBLE}
        }

        val nine: Button = findViewById(R.id.button9n);
        nine.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "9");
            if(passwordEditText.text.length == 4){empty.visibility= View.VISIBLE}
            if(passwordEditText.text.length > 0){delete.visibility= View.VISIBLE}

        }

        val zero: Button = findViewById(R.id.button0n);
        zero.setOnClickListener{
            passwordEditText.setText(passwordEditText.text.toString() + "0");
            if(passwordEditText.text.length == 4){empty.visibility= View.VISIBLE}
            if(passwordEditText.text.length > 0){delete.visibility= View.VISIBLE}
        }
    }
}