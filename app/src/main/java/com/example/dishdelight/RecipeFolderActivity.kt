package com.example.dishdelight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeFolderActivity : AppCompatActivity() {
    private lateinit var folderName: String

    private  lateinit var btnBack : ImageButton
    private lateinit var tvFolderName : TextView
    private  lateinit var recipeRecycler : RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_folder)
        val userid = FirebaseAuth.getInstance().currentUser!!.uid
        folderName = intent.getStringExtra("FOLDER_NAME") ?: "Default Folder Name"

        btnBack = findViewById(R.id.backBtn2)
        tvFolderName = findViewById(R.id.folderName)
        recipeRecycler = findViewById(R.id.recipFolderRecyclerView)

        recipeRecycler.layoutManager = LinearLayoutManager(this)
        tvFolderName.text = folderName

        btnBack.setOnClickListener{
            finish()
        }

        getSavedRecipes(userid, folderName )
    }
    private fun getSavedRecipes( userid : String, folderName: String) {
        RetrofitClient.getClient().create(RecipeApiService::class.java)
            .getSavedRecipes(userid, folderName).enqueue(object : Callback<List<Recipe>> {
                override fun onResponse(call: Call<List<Recipe>>, response: Response<List<Recipe>>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            recipeAdapter = RecipeAdapter(userid, it)
                            recipeRecycler.adapter = recipeAdapter
                        }
                    } else {
                        Toast.makeText(this@RecipeFolderActivity, "Failed to fetch saved recipes", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                    Toast.makeText(this@RecipeFolderActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}