package com.example.dishdelight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
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
        val userid = FirebaseAuth.getInstance().currentUser!!.uid

        btnBack = findViewById(R.id.backBtn3)
        etvName = findViewById(R.id.name_input)
        btnUpdateProfile = findViewById(R.id.confirmButton)

        btnBack.setOnClickListener{
            finish()
        }

        val name = etvName.text.toString()
        updateUserProfile(userid, name)
    }
    fun updateUserProfile(userId: String, username: String) {

        val request = ProfileUpdateRequest(userId, username)
        val apiService = RetrofitClient.getClient().create(UserService::class.java)
        val call = apiService.updateUserProfile(request) // API call

        call.enqueue(object : Callback<ProfileUpdateResponse> {
            override fun onResponse(call: Call<ProfileUpdateResponse>, response: Response<ProfileUpdateResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        println(it.message)
                    }
                } else {
                    println("Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ProfileUpdateResponse>, t: Throwable) {
                println("Failure: ${t.message}")
            }
        })
    }

}