package com.example.pawmart

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast

class CatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat)
        //back button code (finishes activity)
        val backButton = findViewById<Button>(R.id.backButton1)
        backButton.setOnClickListener {
            finish()
        }

    }
}