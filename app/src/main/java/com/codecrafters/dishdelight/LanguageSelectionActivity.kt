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

    // Array of available languages
    private val languageArray = arrayOf("English", "Zulu", "Afrikaans")
    private var selectedLanguageIndex = -1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_selection)

        btnBack = findViewById(R.id.backBtn)
        selectLanguageCard = findViewById(R.id.selectLanguage)
        tvLanguage = findViewById(R.id.tvLanguage)
        btnConfirmLanguage = findViewById(R.id.confirmButton)

        // Set onClick listener for the back button
        btnBack.setOnClickListener {
            finish()
        }

        // Set onClick listener for the language selection card
        selectLanguageCard.setOnClickListener {
            showLanguageDialog()
        }

        // Set onClick listener for the confirm button
        btnConfirmLanguage.setOnClickListener {
            confirmLanguageSelection()
        }
    }

    // Function to display a dialog for selecting the language
    private fun showLanguageDialog() {
        val builder = AlertDialog.Builder(this) // Create an AlertDialog builder
        builder.setTitle("Select Language") // Set the dialog title
            .setSingleChoiceItems(languageArray, selectedLanguageIndex) { _, which ->
                selectedLanguageIndex = which // Update the selected language index
            }
            .setPositiveButton("Ok") { dialog, _ -> // Positive button to confirm selection
                if (selectedLanguageIndex != -1) {
                    tvLanguage.text = languageArray[selectedLanguageIndex] // Update TextView with selected language
                }
                dialog.dismiss() // Dismiss the dialog
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss() // Dismiss the dialog on cancel
            }
            .setNeutralButton("Clear") { dialog, _ -> // Neutral button to clear selection
                selectedLanguageIndex = -1 // Reset selection index
                tvLanguage.text = "" // Clear the displayed language
                dialog.dismiss() // Dismiss the dialog
            }

        builder.show()
    }

    // Function to confirm the language selection
    private fun confirmLanguageSelection() {
        if (selectedLanguageIndex != -1) { // Check if a language is selected
            // Map the selected index to the corresponding language code
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

            // Show a toast message to indicate the selected language
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
            // Show a toast message if no language is selected
            Toast.makeText(this, "Please select a language", Toast.LENGTH_SHORT).show()
        }
    }
}
