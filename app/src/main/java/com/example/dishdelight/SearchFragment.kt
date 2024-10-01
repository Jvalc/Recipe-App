package com.example.dishdelight

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private lateinit var searchIngredient: EditText
    private lateinit var searchDiet: EditText
    private lateinit var searchCuisine: EditText
    private lateinit var searchBtn: Button
    private val db: FirebaseFirestore = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchIngredient = view.findViewById(R.id.searchIngredient)
        searchDiet = view.findViewById(R.id.searchDiet)
        searchCuisine = view.findViewById(R.id.searchCuisine)
        searchBtn = view.findViewById(R.id.searchBtn)

        searchBtn.setOnClickListener {
        }
    }
    private fun searchRecipes(ingredients: String, cuisine: String, dietaryPreference: String) {

        val apiService = RetrofitClient.getClient().create(RecipeApiService::class.java)
        val call = apiService.searchRecipes(ingredients, cuisine, dietaryPreference) // API call

        call.enqueue(object : Callback<SearchResponse> {
                override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                    if (response.isSuccessful) {
                        val recipes = response.body()?.results
                        Log.d("SearchActivity", "Recipes: $recipes")
                    } else {
                        // Handle the error response
                        Log.e("SearchActivity", "Error: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    // Handle the failure
                    Log.e("SearchActivity", "Failure: ${t.message}")
                }
            })
    }
}