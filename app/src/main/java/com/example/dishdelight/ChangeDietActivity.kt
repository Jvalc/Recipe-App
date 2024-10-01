package com.example.dishdelight

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import retrofit2.Call

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
        val userid = FirebaseAuth.getInstance().currentUser!!.uid

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

        val text = tvDiets.text.toString()
        val stringList = text.split(",").map { it.trim() } // Using `trim()` to remove any leading/trailing spaces

        updateUserPreferences(userid, stringList )

    }
    fun updateUserPreferences(userId: String, preferences: List<String>) {
        val request = UpdatePreferencesRequest(preferences)
        val apiService = RetrofitClient.getClient().create(UserService::class.java)
        val call = apiService.updateUserPreferences(userId, request) // API call

        call.enqueue(object : retrofit2.Callback<UpdatePreferencesResponse> {
            override fun onResponse(call: Call<UpdatePreferencesResponse>, response: retrofit2.Response<UpdatePreferencesResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        println(it.message)
                    }
                } else {
                    println("Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<UpdatePreferencesResponse>, t: Throwable) {
                println("Failure: ${t.message}")
            }
        })
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