package ru.fefu.activitytracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class EnterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter)
        val registration_button : Button =  findViewById(R.id.button_continue_enter);
        val arrow_back : ImageView =  findViewById(R.id.arrow_back);
        registration_button.setOnClickListener{
            //startActivity(Intent(this,RegistrationActivity::class.java))
            //TODO проверку введенных данных
        }
        arrow_back.setOnClickListener{
            startActivity(Intent(this,MainScreenActivity::class.java))
        }
    }
}