package com.example.dishdelight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView

class TermsActivity : AppCompatActivity() {
    private  lateinit var btnBack : ImageButton
    private lateinit var tvTerms : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms)

        btnBack = findViewById(R.id.backBtn4)
        tvTerms = findViewById(R.id.tvTerms)

        btnBack.setOnClickListener{
            finish()
        }
    }
}