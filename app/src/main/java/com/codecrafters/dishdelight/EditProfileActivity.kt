package com.codecrafters.dishdelight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class EditProfileActivity : AppCompatActivity() {
    private  lateinit var btnBack : ImageView
    private lateinit var etvName : EditText
    private lateinit var tvEmail : TextView
    private lateinit var btnUpdateProfile : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        // Get the current user ID and email from Firebase Authentication
        val userid = FirebaseAuth.getInstance().currentUser!!.uid
        val userEmail = FirebaseAuth.getInstance().currentUser!!.email

        btnBack = findViewById(R.id.backBtn3)
        etvName = findViewById(R.id.name_input)
        tvEmail = findViewById(R.id.tvEmail)
        btnUpdateProfile = findViewById(R.id.confirmButton)

        // Set up back button to finish the activity and go back to the previous screen
        btnBack.setOnClickListener{
            finish()
        }

        // Display the current user's email in the email TextView
        tvEmail.text = userEmail

        // Set up the update button to trigger profile update
        btnUpdateProfile.setOnClickListener {
            val name = etvName.text.toString() // Get the new name from EditText
            updateProfile(userid, name) // Call updateProfile with user ID and new name
        }
    }
    // Define updateProfile method to send updated profile information to the server
    private fun updateProfile(userId: String, username: String) {
        // Create a request object with user ID and username
        val request = UserProfileUpdateRequest(userId, username)

        // Make a network call using Retrofit to update the profile
        RetrofitClient.getClient().create(UserService::class.java)
            .updateUserProfile(request).enqueue(object : Callback<UpdateResponse> {
            override fun onResponse(call: Call<UpdateResponse>, response: Response<UpdateResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditProfileActivity, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@EditProfileActivity, "Failed to save changes", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UpdateResponse>, t: Throwable) {
                Toast.makeText(this@EditProfileActivity, "Failure: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}