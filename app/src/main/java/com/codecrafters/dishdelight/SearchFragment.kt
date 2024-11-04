package com.codecrafters.dishdelight

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private lateinit var searchIngredient: EditText
    private lateinit var searchCuisine: EditText
    private lateinit var searchBtn: Button

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
        searchCuisine = view.findViewById(R.id.searchCuisine)
        searchBtn = view.findViewById(R.id.searchBtn)

        searchBtn.setOnClickListener {
            searchRecipes()
        }
    }

    private fun searchRecipes() {
        val gson = Gson()

        val ingredients = searchIngredient.text.toString()
        val cuisine = searchCuisine.text.toString()

        // Create a HashMap to hold query parameters
        val queryParams = mutableMapOf<String, String>()

        // Add parameters to the map only if they are not empty
        if (!ingredients.isNullOrEmpty()) {
            queryParams["ingredients"] = ingredients
        }
        if (!cuisine.isNullOrEmpty()) {
            queryParams["cuisineType"] = cuisine
        }

        // If queryParams is empty, notify the user and don't make the API call
        if (queryParams.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter at least one search parameter", Toast.LENGTH_SHORT).show()
            return
        }

        // Call the API using Retrofit with the valid queryParams map
        RetrofitClient.getClient().create(RecipeApiService::class.java)
            .searchRecipes(queryParams)
            .enqueue(object : Callback<SearchResponse> {

                override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                    if (response.isSuccessful) {
                        val recipes = response.body()?.results ?: emptyList()
                        if (recipes.isNotEmpty()) {
                            val recipesJson = gson.toJson(recipes)
                            // Pass results to the next activity
                            val intent = Intent(requireActivity(), SearchRecipeActivity::class.java)
                            intent.putExtra("RECIPE_RESULTS_JSON", recipesJson)
                            startActivity(intent)
                        } else {
                            Toast.makeText(requireContext(), "No recipes found matching your criteria.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "No recipes found matching your criteria.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

}