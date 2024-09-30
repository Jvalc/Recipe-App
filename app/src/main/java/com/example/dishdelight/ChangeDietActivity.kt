package com.example.dishdelight

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.card.MaterialCardView

class ChangeDietActivity : AppCompatActivity() {
    private  lateinit var btnBack : ImageButton
    private lateinit var selectCard : MaterialCardView
    private lateinit var tvDiets : TextView
    private lateinit var selectedDiets: BooleanArray
    val dietList = ArrayList<Int>()
    val dietArray = arrayOf("Gluten-free", "Vegan", "Fast food")
    private lateinit var btnUpdateDiet : Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_diet)

        btnBack = findViewById(R.id.backBtn5)
        selectCard = findViewById(R.id.selectDiet)
        tvDiets = findViewById(R.id.tvDiets)
        btnUpdateDiet = findViewById(R.id.confirmButton)
        selectedDiets = BooleanArray(dietArray.size)

        selectedDiets = BooleanArray(dietArray.size)

        btnBack.setOnClickListener{
            finish()
        }

        selectCard.setOnClickListener{
            showDietsDialog()
        }

    }

    private fun showDietsDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Diet")
            .setCancelable(false)
            .setMultiChoiceItems(dietArray, selectedDiets) { dialog, which, isChecked ->
                if (isChecked) {
                    dietList.add(which)
                } else {
                    dietList.remove(which)
                }
            }
            .setPositiveButton("Ok") { dialog, which ->
                val stringBuilder = StringBuilder()
                for (i in dietList.indices) {
                    stringBuilder.append(dietArray[dietList[i]])
                    if (i != dietList.size - 1) {
                        stringBuilder.append(", ")
                    }
                }
                tvDiets.text = stringBuilder.toString()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            .setNeutralButton("Clear") { dialog, which ->
                selectedDiets.fill(false) // Clear the selected diets
                dietList.clear()
                tvDiets.text = ""
            }

        builder.show()

    }
}