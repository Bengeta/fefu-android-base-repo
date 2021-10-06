package ru.fefu.activitytracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        val registration_button : Button =  findViewById(R.id.button_continue_enter);
        val login_button : Button =  findViewById(R.id.login_button);
        registration_button.setOnClickListener{
            startActivity(Intent(this,RegistrationActivity::class.java))
        }
        login_button.setOnClickListener{
            startActivity(Intent(this,EnterActivity::class.java))
        }
    }
}