package com.example.dishdelight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchRecipeActivity : AppCompatActivity() {
    private lateinit var recipeRecyclerView: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter
    private lateinit var recipeList: List<Recipe>
    private  lateinit var btnBack : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_recipe)

        // Initialize RecyclerView
        recipeRecyclerView = findViewById(R.id.searchRecyclerView)
        recipeRecyclerView.layoutManager = LinearLayoutManager(this)

        // Get the list of recipes from the intent
        recipeList = intent.getParcelableArrayListExtra("recipeList") ?: emptyList()
        // Set up the adapter
        recipeAdapter = RecipeAdapter(this, recipeList)
        recipeRecyclerView.adapter = recipeAdapter

        btnBack.setOnClickListener{
            finish()
        }
    }
}