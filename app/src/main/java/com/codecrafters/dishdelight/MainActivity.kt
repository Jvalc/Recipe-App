package com.codecrafters.dishdelight

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var database: AppDatabase


    private val REQUEST_CODE = 1001 // Define a request code
    private val PREFS_NAME = "AppPreferences"
    private val PREFS_KEY_PERMISSION_REQUESTED = "permission_requested"

    override fun onCreate(savedInstanceState: Bundle?) {
        loadLocale() // Load the saved locale before setting the content view
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        val userid = FirebaseAuth.getInstance().currentUser!!.uid

        requestNotificationPermission()
        // Get FCM registration token
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Log.d(TAG, "FCM Token: $token")
            sendFcmTokenToServer(userid, token)
        }

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    loadFragment(DashboardFragment())
                    item.setIcon(R.drawable.baseline_home_selected)
                }
                R.id.nav_search -> {
                    loadFragment(SearchFragment())
                    item.setIcon(R.drawable.search_selected)
                }
                R.id.nav_folders -> {
                    loadFragment(FoldersFragment())
                    item.setIcon(R.drawable.heart_selected)
                }
                R.id.nav_settings -> {
                    loadFragment(SettingsFragment())
                    item.setIcon(R.drawable.settings_selected)
                }
            }
            true
        }

        if (savedInstanceState == null) {
            loadFragment(DashboardFragment())
        }
    }
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    101
                )
            }
        }
    }
    private fun sendFcmTokenToServer(userId: String, fcmToken: String) {
        val fcmTokenRequest = FcmTokenRequest(fcmToken)

        RetrofitClient.getClient().create(UserService::class.java)
            .sendFcmToken(userId, fcmTokenRequest).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "FCM token sent successfully", Toast.LENGTH_SHORT)
                } else {
                    println("Error sending FCM token: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handle network error
                println("Failed to send FCM token: ${t.message}")
            }
        })

    }
    private fun loadLocale() {
        val sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "en") // Default is English
        setLocale(language ?: "en")
    }
    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}
