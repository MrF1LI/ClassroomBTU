package com.example.classroombtu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ClassroomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide() // to hide action bar
        setContentView(R.layout.activity_classroom)
    }
}