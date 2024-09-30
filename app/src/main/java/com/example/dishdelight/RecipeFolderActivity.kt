package com.example.dishdelight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecipeFolderActivity : AppCompatActivity() {
    private  lateinit var btnBack : ImageButton
    private lateinit var tvFolderName : TextView
    private  lateinit var recipeRecycler : RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter
    private var recipeList: MutableList<Recipe> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_folder)

        btnBack = findViewById(R.id.backBtn2)
        tvFolderName = findViewById(R.id.folderName)
        recipeRecycler = findViewById(R.id.recipFolderRecyclerView)
        recipeRecycler.layoutManager = LinearLayoutManager(this)

        recipeAdapter = RecipeAdapter(this, recipeList)
        recipeRecycler.adapter = recipeAdapter

        btnBack.setOnClickListener{
            finish()
        }
    }
}