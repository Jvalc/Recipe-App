package com.example.dishdelight

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call

class RecipeFolderActivity : AppCompatActivity() {
    private  lateinit var btnBack : ImageButton
    private lateinit var tvFolderName : TextView
    private  lateinit var recipeRecycler : RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter
    private var recipeList: MutableList<Recipe> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_folder)
        val userid = FirebaseAuth.getInstance().currentUser!!.uid

        btnBack = findViewById(R.id.backBtn2)
        tvFolderName = findViewById(R.id.folderName)
        recipeRecycler = findViewById(R.id.recipFolderRecyclerView)

        btnBack.setOnClickListener{
            finish()
        }
        val name = tvFolderName.text.toString()

        getSavedRecipes(userid, name )
    }

    fun getSavedRecipes(userId: String, folderName: String) {

        val apiService = RetrofitClient.getClient().create(RecipeApiService::class.java)
        val call = apiService.getSavedRecipes(userId, folderName) // API call

        call.enqueue(object : retrofit2.Callback<List<Recipe>> {
            override fun onResponse(call: Call<List<Recipe>>, response: retrofit2.Response<List<Recipe>>) {
                if (response.isSuccessful) {
                    val recipeResponse = response.body()
                    recipeResponse?.let{
                        for (Recipe in it){
                            recipeList.add(Recipe)
                        }
                    }
                    recipeRecycler.layoutManager = LinearLayoutManager(applicationContext)

                    recipeAdapter = RecipeAdapter(applicationContext , recipeList)
                    recipeRecycler.adapter = recipeAdapter
                    recipeAdapter.notifyDataSetChanged()
                } else {
                }
            }

            override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {
                // Handle failure
                t.printStackTrace()
            }
        })
    }
}