package com.codecrafters.dishdelight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SearchRecipeActivity : AppCompatActivity() {
    private lateinit var searchRecyclerView: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter
    private  lateinit var btnBack : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_recipe)
        val userid = FirebaseAuth.getInstance().currentUser!!.uid

        // Initialize RecyclerView
        searchRecyclerView = findViewById(R.id.searchRecyclerView)
        btnBack = findViewById(R.id.backBtn1)
        searchRecyclerView.layoutManager = LinearLayoutManager(this)

        // Get the JSON string from the intent
        val recipesJson = intent.getStringExtra("RECIPE_RESULTS_JSON")

        // Deserialize the JSON string back to a list of Recipe objects
        if (recipesJson != null) {
            val gson = Gson()
            val recipeListType = object : TypeToken<List<Recipe>>() {}.type
            val recipes: List<Recipe> = gson.fromJson(recipesJson, recipeListType)

            // Set up the RecyclerView
            recipeAdapter = RecipeAdapter(userid, recipes)
            searchRecyclerView.adapter = recipeAdapter
        }

        btnBack.setOnClickListener{
            finish()
        }
    }
}