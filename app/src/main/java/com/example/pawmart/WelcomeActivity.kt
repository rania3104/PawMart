package com.example.pawmart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
    }
    //function for the login button
    fun onLoginClicked(view: View) {
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val name = nameEditText.text.toString().trim()

        //shared preferences
        if (name.isNotEmpty()) {
            //saving the name in shared preferences if the name is not empty
            val sharedPreferences = getSharedPreferences("PawMartPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("userName", name)
            editor.apply()

            //after saving the activity changes to the main activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        } else {//if name is empty show error
            nameEditText.error = "Please enter your name"
        }
    }
}
