package com.example.dishdelight

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView : BottomNavigationView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView= findViewById(R.id.bottom_navigation)

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
}