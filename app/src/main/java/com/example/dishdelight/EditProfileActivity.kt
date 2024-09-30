package com.example.dishdelight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileActivity : AppCompatActivity() {
    private  lateinit var btnBack : ImageButton
    private lateinit var etvName : EditText
    private lateinit var userId : String
    private lateinit var btnUpdateProfile : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        btnBack = findViewById(R.id.backBtn3)
        etvName = findViewById(R.id.name_input)
        btnUpdateProfile = findViewById(R.id.confirmButton)

        btnBack.setOnClickListener{
            finish()
        }

        fetchUserProfile()
        updateProfile()
    }

    private fun fetchUserProfile() {
        val apiService = RetrofitClient.getClient().create(UserService::class.java)
        val call = apiService.getUserProfile()

        call.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                if (response.isSuccessful) {
                    val profileResponse = response.body()
                    if (profileResponse != null) {
                        // Update UI with user profile data
                        etvName.setText(profileResponse.name)
                    }
                } else {
                    // Handle error response
                    Toast.makeText(this@EditProfileActivity, "Failed to fetch profile", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                // Handle failure
                Toast.makeText(this@EditProfileActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateProfile() {
        val apiService = RetrofitClient.getClient().create(UserService::class.java)

        val profileUpdateRequest = ProfileUpdateRequest(
            userId = userId,
            username = etvName.text.toString()
        )

        val call = apiService.updateProfile(profileUpdateRequest)

        call?.enqueue(object : Callback<ProfileUpdateResponse?> {
            override fun onResponse(
                call: Call<ProfileUpdateResponse?>,
                response: Response<ProfileUpdateResponse?>
            ) {
                if (response.isSuccessful) {
                    // Handle successful response
                    Toast.makeText(
                        this@EditProfileActivity,
                        response.body()?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    finish() // Optionally close the activity
                } else {
                    // Handle error response
                    Toast.makeText(
                        this@EditProfileActivity,
                        "Update failed: ${response.errorBody()?.string()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ProfileUpdateResponse?>, t: Throwable) {
                // Handle failure
                Toast.makeText(this@EditProfileActivity, "Error: ${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

}