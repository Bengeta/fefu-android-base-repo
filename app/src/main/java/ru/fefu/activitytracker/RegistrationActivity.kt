package ru.fefu.activitytracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        val items = listOf("Male", "Female")
        val adapter = ArrayAdapter(this, R.layout.list_view, items)
        var element : TextInputLayout= findViewById(R.id.gender);
        (element as? AutoCompleteTextView)?.setAdapter(adapter)
        val arrow_back : ImageView =  findViewById(R.id.arrow_back);
        val registration_button : Button =  findViewById(R.id.button_continue_enter);
        registration_button.setOnClickListener{
            //startActivity(Intent(this,RegistrationActivity::class.java))
            //TODO проверку заполнения полей
        }
        arrow_back.setOnClickListener{
            startActivity(Intent(this,MainScreenActivity::class.java))
        }
    }

}