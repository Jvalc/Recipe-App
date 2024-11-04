package com.codecrafters.dishdelight

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeDietActivity : AppCompatActivity() {
    private  lateinit var btnBack : ImageButton

    private lateinit var selectCard : MaterialCardView
    private lateinit var tvDiets : TextView
    private lateinit var selectedDiets: BooleanArray

    // Array to store the diet options and selected items
    val dietList = ArrayList<Int>()
    val dietArray = arrayOf(
        "Fast food",
        "Vegetarian",
        "Vegan",
        "Paleo",
        "Keto",
        "Gluten-Free",
        "Dairy-Free",
        "Pescatarian",
        "Mediterranean",
        "Low-Carb",
        "Low-Fat",
        "Whole30",
        "Carnivore",
        "Flexitarian",
        "High-Protein",
        "Sugar-Free"
    );

    private lateinit var btnUpdateDiet : Button

    @SuppressLint("MissingInflatedId")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_diet)
        // Get current user ID from FirebaseAuth
        val userid = FirebaseAuth.getInstance().currentUser!!.uid

        // Initialize UI components
        btnBack = findViewById(R.id.backBtn5)
        selectCard = findViewById(R.id.selectDiet)
        tvDiets = findViewById(R.id.tvDiets)
        btnUpdateDiet = findViewById(R.id.confirmButton)
        selectedDiets = BooleanArray(dietArray.size)

        selectedDiets = BooleanArray(dietArray.size)

        // Set up the back button to finish the activity
        btnBack.setOnClickListener{
            finish()
        }

        // Set up the diet selection card to show diet options dialog
        selectCard.setOnClickListener{
            showDietsDialog()
        }

        // Set up the update button to send selected diets to the server
        btnUpdateDiet.setOnClickListener {
            val text = tvDiets.text.toString()
            val stringList = text.split(",").map { it.trim() } // Using `trim()` to remove any leading/trailing spaces

            updateUserPreferences(userid, stringList )
        }
    }
    // Function to update user dietary preferences using Retrofit
    fun updateUserPreferences(userId: String, preferences: List<String>) {

        val request = UpdateDietaryPreferencesRequest(preferences)

        // Making a PATCH request through Retrofit
        RetrofitClient.getClient().create(UserService::class.java)
            .updateDietaryPreferences(userId, request)
            .enqueue(object : Callback<UpdateResponse> {
                override fun onResponse(call: Call<UpdateResponse>, response: Response<UpdateResponse>) {
                    if (response.isSuccessful) {
                        Log.d("ChangeDietActivity", "Preferences updated successfully for user $userId")
                        Toast.makeText(this@ChangeDietActivity, "Preference updated successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Log.e("ChangeDietActivity", "Failed to update preferences for user $userId, response code: ${response.code()}")
                        Toast.makeText(this@ChangeDietActivity, "Failed to update preferences", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UpdateResponse>, t: Throwable) {
                    Toast.makeText(this@ChangeDietActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
    // Function to display a dialog for selecting diets
    fun showDietsDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Diet")
            .setCancelable(false)
            .setMultiChoiceItems(dietArray, selectedDiets) { dialog, which, isChecked ->
                Log.d("ChangeDietActivity", "${dietArray[which]} option selected: $isChecked")
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