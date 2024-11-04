package com.codecrafters.dishdelight

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView

class LanguageSelectionActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var selectLanguageCard: MaterialCardView // Update to MaterialCardView
    private lateinit var tvLanguage: TextView
    private lateinit var btnConfirmLanguage: Button

    private val languageArray = arrayOf("English", "Zulu", "Afrikaans")
    private var selectedLanguageIndex = -1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_selection)

        btnBack = findViewById(R.id.backBtn)
        selectLanguageCard = findViewById(R.id.selectLanguage) // Now correctly assigned as MaterialCardView
        tvLanguage = findViewById(R.id.tvLanguage)
        btnConfirmLanguage = findViewById(R.id.confirmButton)

        btnBack.setOnClickListener {
            finish()
        }

        selectLanguageCard.setOnClickListener {
            showLanguageDialog()
        }

        btnConfirmLanguage.setOnClickListener {
            confirmLanguageSelection()
        }
    }

    private fun showLanguageDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Language")
            .setSingleChoiceItems(languageArray, selectedLanguageIndex) { _, which ->
                selectedLanguageIndex = which
            }
            .setPositiveButton("Ok") { dialog, _ ->
                if (selectedLanguageIndex != -1) {
                    tvLanguage.text = languageArray[selectedLanguageIndex]
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setNeutralButton("Clear") { dialog, _ ->
                selectedLanguageIndex = -1
                tvLanguage.text = ""
                dialog.dismiss()
            }

        builder.show()
    }

    private fun confirmLanguageSelection() {
        if (selectedLanguageIndex != -1) {
            val selectedLanguageCode = when (selectedLanguageIndex) {
                0 -> "en"
                1 -> "zu"
                2 -> "af"
                else -> "en"
            }

            // Save selected language to SharedPreferences
            val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
            editor.putString("My_Lang", selectedLanguageCode)
            editor.apply()

            // Show a toast message
            Toast.makeText(
                this,
                "${languageArray[selectedLanguageIndex]} selected as the language.",
                Toast.LENGTH_SHORT
            ).show()

            // Restart MainActivity to apply changes
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Please select a language", Toast.LENGTH_SHORT).show()
        }
    }
}
