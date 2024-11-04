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
        val userid = FirebaseAuth.getInstance().currentUser!!.uid
        val userEmail = FirebaseAuth.getInstance().currentUser!!.email

        btnBack = findViewById(R.id.backBtn3)
        etvName = findViewById(R.id.name_input)
        tvEmail = findViewById(R.id.tvEmail)
        btnUpdateProfile = findViewById(R.id.confirmButton)


        btnBack.setOnClickListener{
            finish()
        }

        tvEmail.text = userEmail

        btnUpdateProfile.setOnClickListener {
            val name = etvName.text.toString()
            updateProfile(userid, name)
        }
    }

    private fun updateProfile(userId: String, username: String) {
        val request = UserProfileUpdateRequest(userId, username)

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